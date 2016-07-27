package com.phototravel.dataCollectors.destinations;

import com.phototravel.Encoder;
import com.phototravel.RequestSender;
import com.phototravel.services.CityService;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.NodeList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class PolskiBusDestinationsGetter {

    @Autowired
    RequestSender requestSender;

    @Autowired
    CityService cityService;

    private static final String PATH = "https://booking.polskibus.com/Pricing/Selections?lang=PL";

    public List<String> getCities() throws ParserConfigurationException, XPathExpressionException, UnsupportedEncodingException {
        String responseStr = "";
        responseStr = requestSender.excutePost(PATH, "");

        List<String> listOfCities = new ArrayList<String>();

        TagNode tagNode = new HtmlCleaner().clean(responseStr);

        org.w3c.dom.Document doc;
        doc = new org.htmlcleaner.DomSerializer(new CleanerProperties()).createDOM(tagNode);

        XPath xpath = XPathFactory.newInstance().newXPath();

        Encoder encoder = new Encoder();
        try {
            //create XPathExpression object
            XPathExpression expr =
                    xpath.compile("//select[@id='PricingForm_FromCity']/option");
            //evaluate expression result on XML document
            NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            for (int i = 1; i < nodes.getLength(); i++) {
                String city =  nodes.item(i).getFirstChild().getNodeValue().toLowerCase();

                city = encoder.encode(city);
                city = city.substring(0,1).toUpperCase()+city.substring(1);

                System.out.println(city);
                listOfCities.add(city);

            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }


        return listOfCities;
    }


    public Map <String, String> getDestinations() throws ParserConfigurationException, XPathExpressionException, UnsupportedEncodingException {
        String responseStr = "";


     /*   Client client = Client.create();
        WebResource webResource = client.resource(PATH);


        ClientResponse response = webResource
                .get(ClientResponse.class);


        if (response.getClientResponseStatus() == ClientResponse.Status.OK) {
            responseStr = response.getEntity(String.class);
        }*/

        responseStr = requestSender.excutePost(PATH, "");


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

                String key2 = java.net.URLDecoder.decode(key, "UTF-8");
                String key3 = java.net.URLDecoder.decode(key, "latin1");

                System.out.println(key+"  "+value+"  "+key2 +"  "+key3);
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
