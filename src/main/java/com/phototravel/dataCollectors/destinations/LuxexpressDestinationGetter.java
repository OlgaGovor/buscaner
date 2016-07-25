package com.phototravel.dataCollectors.destinations;

import com.phototravel.dataCollectors.outRequests.SendRequestLuxexpress;
import com.sun.jersey.api.client.ClientResponse;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Olga_Govor on 7/22/2016.
 */
@Service
public class LuxexpressDestinationGetter {

//get json with all bus stops
    //StopId
    //StopName
    //Slug  - key for request
    private static final String PATH = "http://ticket.luxexpress.eu/pl/Stops/GetAllBusStops";

    private static final String CONTENTTYPE = "application/json";

    public Map<String, String> getDestinations() throws ParserConfigurationException, XPathExpressionException, ParseException, JSONException {

        SendRequestLuxexpress sendRequestLuxexpress = new SendRequestLuxexpress();

        ClientResponse response = sendRequestLuxexpress.sendGetRequest(sendRequestLuxexpress.createWebResource(PATH), CONTENTTYPE);
        String responseStr = sendRequestLuxexpress.getResponseString(response);


        Map <String, String> listOfDestinations = new LinkedHashMap<String, String>();
//
//        TagNode tagNode = new HtmlCleaner().clean(responseStr);
//
//        org.w3c.dom.Document doc;
//        doc = new org.htmlcleaner.DomSerializer(new CleanerProperties()).createDOM(tagNode);
//
//        XPath xpath = XPathFactory.newInstance().newXPath();
//
//        String key="";
//        try {
//            //create XPathExpression object
//            XPathExpression expr =
//                    xpath.compile("//pre");
//            //evaluate expression result on XML document
//            NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
//            for (int i = 1; i < nodes.getLength(); i++) {
//                key =  nodes.item(i).getFirstChild().getNodeValue();
//            }
//        } catch (XPathExpressionException e) {
//            e.printStackTrace();
//        }

        JSONParser parser = new JSONParser();

        Object obj = null;

        obj = parser.parse(responseStr);



        JSONArray array1 = (JSONArray)obj;


        for(int i = 0; i < array1.size(); i++) {


            JSONObject ob = (JSONObject) array1.get(i);

            String key = ob.get("StopName").toString();
            String value = ob.get("Slug").toString();
            listOfDestinations.put(key, value);
            System.out.println(key+"  "+value);
        }

            System.out.println();

        return listOfDestinations;
    }
}
