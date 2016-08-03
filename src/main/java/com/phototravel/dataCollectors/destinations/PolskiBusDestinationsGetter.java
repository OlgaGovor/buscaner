package com.phototravel.dataCollectors.destinations;

import com.phototravel.Encoder;
import com.phototravel.RequestSender;
import com.phototravel.repository.CompanyRepository;
import com.phototravel.repository.DestinationRepositoty;
import com.phototravel.services.RouteService;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
    DestinationRepositoty destinationRepositoty;

    @Autowired
    RouteService routeService;

    @Autowired
    CompanyRepository companyRepository;

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

        responseStr = requestSender.excutePost(PATH, "");

        Map <String, String> listOfDestinations = new LinkedHashMap <String, String>();

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
                String key =  nodes.item(i).getFirstChild().getNodeValue().toLowerCase();
                String value = nodes.item(i).getAttributes().getNamedItem("value").getNodeValue();

                key = encoder.encode(key);
                key = key.substring(0,1).toUpperCase()+key.substring(1);

                System.out.println(key+"  "+value);
                listOfDestinations.put(key, value);
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        return listOfDestinations;
    }

    public void getRoutesForDb() throws ParserConfigurationException, XPathExpressionException, UnsupportedEncodingException, JSONException {
        String responseStr = "";
        responseStr = requestSender.excutePost(PATH, "");

        TagNode tagNode = new HtmlCleaner().clean(responseStr);

        org.w3c.dom.Document doc;
        doc = new org.htmlcleaner.DomSerializer(new CleanerProperties()).createDOM(tagNode);

        XPath xpath = XPathFactory.newInstance().newXPath();

        //get JSON with routes from response
        System.out.println(responseStr);
        String connected ="";
        XPathExpression expr2 = xpath.compile("//script");
        NodeList nodes = (NodeList) expr2.evaluate(doc, XPathConstants.NODESET);
        connected = nodes.item(nodes.getLength()-1).getFirstChild().getNodeValue();
        connected = connected.substring(24, connected.indexOf(";")-1);

        JSONObject obj = new JSONObject(connected);

        Integer companyId = companyRepository.findCompanyByName("PolskiBus");
        //get request values for PolskiBus
        List<String> listRequestValue = destinationRepositoty.getRequestValuesByCompanyId(companyId);

        //for all request values create routes
        for (String fromRequestValue: listRequestValue) {

            Integer from_dest_id = destinationRepositoty.getDestIdByRequestValue(fromRequestValue);
            Integer from_city_id = destinationRepositoty.getCityIdByRequestValue(fromRequestValue);

            JSONArray arr = obj.getJSONArray(fromRequestValue);
            for (int i = 0; i < arr.length(); i++)
            {
                String toRequestValue = arr.getJSONObject(i).getString("First");

                Integer to_dest_id = destinationRepositoty.getDestIdByRequestValue(toRequestValue);
                Integer to_city_id = destinationRepositoty.getCityIdByRequestValue(toRequestValue);

                System.out.println(from_dest_id+" "+to_dest_id+" "+ from_city_id+" "+ to_city_id+" "+ companyId);

                try {
                    routeService.createRoute(from_dest_id, to_dest_id, from_city_id, to_city_id, companyId, true, false);
                }
                catch (Exception e){}
            }
        }
    }

}
