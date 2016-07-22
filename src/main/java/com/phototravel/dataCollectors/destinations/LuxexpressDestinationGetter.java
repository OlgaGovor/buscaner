package com.phototravel.dataCollectors.destinations;

import com.phototravel.RequestSender;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.NodeList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Olga_Govor on 7/22/2016.
 */
@Service
public class LuxexpressDestinationGetter {

    @Autowired
    RequestSender requestSender;
//get json with all bus stops
    //StopId
    //StopName
    //Slug  - key for request
    private static final String PATH = "http://ticket.luxexpress.eu/pl/Stops/GetAllBusStops";

    public Map<String, String> getDestinations() throws ParserConfigurationException, XPathExpressionException {
        String responseStr = "";


     /*   Client client = Client.create();
        WebResource webResource = client.resource(PATH);


        ClientResponse response = webResource
                .get(ClientResponse.class);


        if (response.getClientResponseStatus() == ClientResponse.Status.OK) {
            responseStr = response.getEntity(String.class);
        }*/

        responseStr = requestSender.excutePost(PATH, "");


        Map <String, String> listOfDestinations = new LinkedHashMap<String, String>();

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
        //connections
//        System.out.println(responseStr);
        String connected ="";
        XPathExpression expr2 = xpath.compile("//script");
        NodeList nodes = (NodeList) expr2.evaluate(doc, XPathConstants.NODESET);
        connected = nodes.item(nodes.getLength()-1).getFirstChild().getNodeValue();
        System.out.println(connected);



        return listOfDestinations;
    }
}
