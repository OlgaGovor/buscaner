package com.buscanner.destinations;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.w3c.dom.NodeList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Olga_Govor on 7/4/2016.
 */
public class GetPolskiBusDestinations {

    private final String PATH = "https://booking.polskibus.com/Pricing/Selections?lang=PL";

    public Map <String, String> getDestinations() throws ParserConfigurationException {
        Client client = Client.create();
        WebResource webResource = client.resource(PATH);

        ClientResponse response = webResource
                .get(ClientResponse.class);

        String responseStr = "";
        if (response.getClientResponseStatus() == ClientResponse.Status.OK) {
            responseStr = response.getEntity(String.class);
        }
        Map <String, String> listOfDestinations = new LinkedHashMap <String, String>();

        TagNode tagNode = new HtmlCleaner().clean(responseStr);

        org.w3c.dom.Document doc;
        doc = new org.htmlcleaner.DomSerializer(new CleanerProperties()).createDOM(tagNode);

        XPath xpath = XPathFactory.newInstance().newXPath();

        try {
            //create XPathExpression object
            XPathExpression expr =
                    xpath.compile("//select[@id='PricingForm_FromCity']/option");
            //evaluate expression result on XML document
            NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            for (int i = 1; i < nodes.getLength(); i++) {
                String key =  nodes.item(i).getFirstChild().getNodeValue().toLowerCase();
                String value = nodes.item(i).getAttributes().getNamedItem("value").getNodeValue();
                listOfDestinations.put(key, value);
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        return listOfDestinations;
    }
}
