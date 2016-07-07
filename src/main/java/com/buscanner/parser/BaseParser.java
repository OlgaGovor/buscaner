package com.buscanner.parser;

import com.buscanner.Route;
import com.buscanner.RouteDetails;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.w3c.dom.NodeList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olga_Govor on 7/1/2016.
 */
abstract public class BaseParser {

    public Route parse(String str, Route route, String companyName, String xPathPrice, String xPathDeparture, String xPathArrival) throws XPathExpressionException, ParserConfigurationException, ParseException {

        List<RouteDetails> routeDetailsList = new ArrayList<RouteDetails>();

        List<String> listPrices = parseResponseString(xPathPrice, str);
        List<String> listTimeOfDepartures = parseResponseString(xPathDeparture, str);
        List<String> listTimeOfArrival = parseResponseString(xPathArrival, str);

        for (int i=0; i<listPrices.size(); i++)
        {
            RouteDetails details = new RouteDetails();

            Double price = parsePrice(listPrices.get(i));
            details.setPrice(price);
            Time timeOfDeparture = parseTime(listTimeOfDepartures.get(i));
            details.setTimeDeparture(timeOfDeparture);
            Time timeOfArrival = parseTime(listTimeOfArrival.get(i));
            details.setTimeArrival(timeOfArrival);
            details.setCompany(companyName);

            routeDetailsList.add(details);
        }
        route.setDetails(routeDetailsList);
        return route;
    }

    public List<String> parseResponseString(String xPathExpression, String str) throws ParserConfigurationException, XPathExpressionException {

        List<String> list = new ArrayList<String>();
        TagNode tagNode = new HtmlCleaner().clean(str);

        org.w3c.dom.Document doc;
        doc = new org.htmlcleaner.DomSerializer(new CleanerProperties()).createDOM(tagNode);

        XPath xpath = XPathFactory.newInstance().newXPath();

        try {
            //create XPathExpression object
            XPathExpression expr =
                    xpath.compile(xPathExpression);
            //evaluate expression result on XML document
            NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < nodes.getLength(); i++) {
                list.add(nodes.item(i).getFirstChild().getNodeValue());
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Double parsePrice(String priceStr){

        Double price = Double.parseDouble(priceStr);
        return price;
    }

    public Time parseTime(String timeStr) throws ParseException {

        DateFormat formatter = new SimpleDateFormat("HH:mm");
        Time price = new java.sql.Time(formatter.parse(timeStr).getTime());
        return price;
    }

//    public List<String> getTimeOfDeparture(String str) throws ParserConfigurationException, XPathExpressionException {
//
//        List<String> listOfDepartures = parseResponseString("//div[contains(@class,'row times')]/div/span[1]", str);
////        List<String> listOfDepartures = new ArrayList<String>();
////
////        TagNode tagNode = new HtmlCleaner().clean(str);
////
////        org.w3c.dom.Document doc;
////        doc = new org.htmlcleaner.DomSerializer(new CleanerProperties()).createDOM(tagNode);
////
////        XPath xpath = XPathFactory.newInstance().newXPath();
////
////        try {
////            //create XPathExpression object
////            XPathExpression expr =
////                    xpath.compile("//div[contains(@class,'row times')]/div/span[1]");
////            //evaluate expression result on XML document
////            NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
////            for (int i = 0; i < nodes.getLength(); i++) {
////                listOfDepartures.add(nodes.item(i).getFirstChild().getNodeValue());
////            }
////        } catch (XPathExpressionException e) {
////            e.printStackTrace();
////        }
////
//        return listOfDepartures;
//    }
//
//    public List<String> getTimeOfArrival(String str) throws ParserConfigurationException, XPathExpressionException {
//
//        List<String> listOfArrival = parseResponseString("//div[contains(@class,'row times')]/div/span[2]", str);
////
////        TagNode tagNode = new HtmlCleaner().clean(str);
////
////        org.w3c.dom.Document doc;
////        doc = new org.htmlcleaner.DomSerializer(new CleanerProperties()).createDOM(tagNode);
////
////        XPath xpath = XPathFactory.newInstance().newXPath();
////
////        try {
////            //create XPathExpression object
////            XPathExpression expr =
////                    xpath.compile("//div[contains(@class,'row times')]/div/span[2]");
////            //evaluate expression result on XML document
////            NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
////            for (int i = 0; i < nodes.getLength(); i++) {
////                listOfArrival.add(nodes.item(i).getFirstChild().getNodeValue());
////            }
////        } catch (XPathExpressionException e) {
////            e.printStackTrace();
////        }
////
//        return listOfArrival;
//    }
//
//    public List<String> getRegularPrice(String str) throws ParserConfigurationException, XPathExpressionException {
//
//        List<String> listOfPrices = parseResponseString("//div[contains (@class, 'regular-fullPrice')]//span[@class = 'amount']", str);
////        TagNode tagNode = new HtmlCleaner().clean(str);
////
////        org.w3c.dom.Document doc;
////        doc = new org.htmlcleaner.DomSerializer(new CleanerProperties()).createDOM(tagNode);
////
////        XPath xpath = XPathFactory.newInstance().newXPath();
////
////        try {
////            //create XPathExpression object
////            XPathExpression expr =
////                    xpath.compile("//div[contains (@class, 'regular-fullPrice')]//span[@class = 'amount']");
////            //evaluate expression result on XML document
////            NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
////            for (int i = 0; i < nodes.getLength(); i++) {
////                listOfPrices.add(nodes.item(i).getFirstChild().getNodeValue());
////            }
////        } catch (XPathExpressionException e) {
////            e.printStackTrace();
////        }
//        return listOfPrices;
//    }
//
//    public List<String> getPromotionPrice(String str) throws ParserConfigurationException, XPathExpressionException {
//
////        TagNode tagNode = new HtmlCleaner().clean(str);
////
////        org.w3c.dom.Document doc;
////        doc = new org.htmlcleaner.DomSerializer(new CleanerProperties()).createDOM(tagNode);
////
////        XPath xpath = XPathFactory.newInstance().newXPath();
////
//        List<String> listOfPrices = new ArrayList<String>();
////        try {
////            //create XPathExpression object
////            XPathExpression expr =
////                    xpath.compile("//div[contains (@class, 'regular-fullPrice')]//span[@class = 'amount']");
////            //evaluate expression result on XML document
////            NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
////            for (int i = 0; i < nodes.getLength(); i++) {
////                listOfPrices.add(nodes.item(i).getFirstChild().getNodeValue());
////                System.out.println(nodes.item(i).getFirstChild().getNodeValue());
////
////            }
////        } catch (XPathExpressionException e) {
////            e.printStackTrace();
////        }
//        return listOfPrices;
//
//    }
//
//    public void parseTime (){
////         row with data "//div[@data-legs][i]"
////         get data legs for promotion request //div[@data-legs][1]/@data-legs
//
//    }
}
