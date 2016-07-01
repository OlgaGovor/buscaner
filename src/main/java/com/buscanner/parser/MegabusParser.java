package com.buscanner.parser;

import com.buscanner.Route;
import com.buscanner.RouteDetails;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.w3c.dom.NodeList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olga_Govor on 7/1/2016.
 */
public class MegabusParser {
    public Route parseMegabus(String str, Route route) throws XPathExpressionException, ParserConfigurationException {

        List<RouteDetails> routeDetailsList = new ArrayList<RouteDetails>();

        List<String> listPrices = getRegularPrice(str);
        List<String> listTimeOfDepartures = getTimeOfDeparture(str);
        List<String> listTimeOfArrival = getTimeOfArrival(str);

        for (int i=0; i<listPrices.size(); i++)
        {
            RouteDetails details = new RouteDetails();

            details.setPrice(listPrices.get(i));
            details.setTimeDeparture(listTimeOfDepartures.get(i));
            details.setTimeArrival(listTimeOfArrival.get(i));
            details.setCompany("Megabus");

            routeDetailsList.add(details);

        }

        route.setDetails(routeDetailsList);

        return route;
    }

    public List<String> getTimeOfDeparture(String str) throws ParserConfigurationException, XPathExpressionException {

        List<String> listOfDepartures = new ArrayList<String>();

        TagNode tagNode = new HtmlCleaner().clean(str);

        org.w3c.dom.Document doc;
        doc = new org.htmlcleaner.DomSerializer(new CleanerProperties()).createDOM(tagNode);

        XPath xpath = XPathFactory.newInstance().newXPath();

        try {
            //create XPathExpression object
            XPathExpression expr =
                    xpath.compile("//ul[contains(@id,'JourneyResylts_OutboundList_GridViewResults')]/li[@class='two']/p[strong[.='Departs']]");
            //evaluate expression result on XML document
            NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < nodes.getLength(); i++) {

                listOfDepartures.add(nodes.item(i).getChildNodes().item(2).getNodeValue().trim());
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        return listOfDepartures;
    }

    public List<String> getTimeOfArrival(String str) throws ParserConfigurationException, XPathExpressionException {

        List<String> listOfArrival = new ArrayList<String>();


        TagNode tagNode = new HtmlCleaner().clean(str);

        org.w3c.dom.Document doc;
        doc = new org.htmlcleaner.DomSerializer(new CleanerProperties()).createDOM(tagNode);

        XPath xpath = XPathFactory.newInstance().newXPath();

        try {
            //create XPathExpression object
            XPathExpression expr =
                    xpath.compile("//ul[contains(@id,'JourneyResylts_OutboundList_GridViewResults')]/li[@class='two']/p[@class='arrive']");
            //evaluate expression result on XML document
            NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < nodes.getLength(); i++) {
                listOfArrival.add(nodes.item(i).getChildNodes().item(2).getNodeValue().trim());
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        return listOfArrival;
    }



    public List<String> getRegularPrice(String str) throws ParserConfigurationException, XPathExpressionException {

        TagNode tagNode = new HtmlCleaner().clean(str);

        org.w3c.dom.Document doc;
        doc = new org.htmlcleaner.DomSerializer(new CleanerProperties()).createDOM(tagNode);

        XPath xpath = XPathFactory.newInstance().newXPath();

        List<String> listOfPrices = new ArrayList<String>();
        try {
            //create XPathExpression object
            XPathExpression expr =
                    xpath.compile("//ul[contains(@id,'JourneyResylts_OutboundList_GridViewResults')]/li[@class='five']/p");
            //evaluate expression result on XML document
            NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < nodes.getLength(); i++) {
                listOfPrices.add(nodes.item(i).getFirstChild().getNodeValue().trim());
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return listOfPrices;

    }

    public List<String> getPromotionPrice(String str) throws ParserConfigurationException, XPathExpressionException {

//        TagNode tagNode = new HtmlCleaner().clean(str);
//
//        org.w3c.dom.Document doc;
//        doc = new org.htmlcleaner.DomSerializer(new CleanerProperties()).createDOM(tagNode);
//
//        XPath xpath = XPathFactory.newInstance().newXPath();
//
        List<String> listOfPrices = new ArrayList<String>();
//        try {
//            //create XPathExpression object
//            XPathExpression expr =
//                    xpath.compile("//div[contains (@class, 'regular-fullPrice')]//span[@class = 'amount']");
//            //evaluate expression result on XML document
//            NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
//            for (int i = 0; i < nodes.getLength(); i++) {
//                listOfPrices.add(nodes.item(i).getFirstChild().getNodeValue());
//                System.out.println(nodes.item(i).getFirstChild().getNodeValue());
//
//            }
//        } catch (XPathExpressionException e) {
//            e.printStackTrace();
//        }
        return listOfPrices;

    }

    public void parseTime (){
//         row with data "//div[@data-legs][i]"
//         get data legs for promotion request //div[@data-legs][1]/@data-legs

    }
}
