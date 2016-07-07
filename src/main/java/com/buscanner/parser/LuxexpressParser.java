package com.buscanner.parser;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Olga_Govor on 6/29/2016.
 */
public class LuxexpressParser extends BaseParser {

    @Override
    public Double parsePrice(String priceStr){
        String [] s = priceStr.split(",");
        String newStr = s[0]+"."+s[1];
        Double price = Double.parseDouble(newStr);
        return price;
    }

    @Override
    public Time parseTime(String timeStr) throws ParseException {

        DateFormat formatter = new SimpleDateFormat("HH:mm");
        Time time = new java.sql.Time(formatter.parse(timeStr).getTime());
        return time;
    }

//    public Route parseLuxExpress(String str, Route route) throws XPathExpressionException, ParserConfigurationException {
//
//        List<RouteDetails> routeDetailsList = new ArrayList<RouteDetails>();
//
//        List<String> listPrices = getRegularPrice(str);
//        List<String> listTimeOfDepartures = getTimeOfDeparture(str);
//        List<String> listTimeOfArrival = getTimeOfArrival(str);
//
//        for (int i=0; i<listPrices.size(); i++)
//        {
//            RouteDetails details = new RouteDetails();
//
//            details.setPrice(listPrices.get(i));
//            details.setTimeDeparture(listTimeOfDepartures.get(i));
//            details.setTimeArrival(listTimeOfArrival.get(i));
//            details.setCompany("Luxexpress");
//
//            routeDetailsList.add(details);
//
//        }
//
//        route.setDetails(routeDetailsList);
//
//        return route;
//    }
//
//    public List<String> getTimeOfDeparture(String str) throws ParserConfigurationException, XPathExpressionException {
//
//        List<String> listOfDepartures = new ArrayList<String>();
//
//        TagNode tagNode = new HtmlCleaner().clean(str);
//
//        org.w3c.dom.Document doc;
//        doc = new org.htmlcleaner.DomSerializer(new CleanerProperties()).createDOM(tagNode);
//
//        XPath xpath = XPathFactory.newInstance().newXPath();
//
//        try {
//            //create XPathExpression object
//            XPathExpression expr =
//                    xpath.compile("//div[contains(@class,'row times')]/div/span[1]");
//            //evaluate expression result on XML document
//            NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
//            for (int i = 0; i < nodes.getLength(); i++) {
//                listOfDepartures.add(nodes.item(i).getFirstChild().getNodeValue());
//            }
//        } catch (XPathExpressionException e) {
//            e.printStackTrace();
//        }
//
//        return listOfDepartures;
//    }
//
//    public List<String> getTimeOfArrival(String str) throws ParserConfigurationException, XPathExpressionException {
//
//        List<String> listOfArrival = new ArrayList<String>();
//
//
//        TagNode tagNode = new HtmlCleaner().clean(str);
//
//        org.w3c.dom.Document doc;
//        doc = new org.htmlcleaner.DomSerializer(new CleanerProperties()).createDOM(tagNode);
//
//        XPath xpath = XPathFactory.newInstance().newXPath();
//
//        try {
//            //create XPathExpression object
//            XPathExpression expr =
//                    xpath.compile("//div[contains(@class,'row times')]/div/span[2]");
//            //evaluate expression result on XML document
//            NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
//            for (int i = 0; i < nodes.getLength(); i++) {
//                listOfArrival.add(nodes.item(i).getFirstChild().getNodeValue());
//            }
//        } catch (XPathExpressionException e) {
//            e.printStackTrace();
//        }
//
//        return listOfArrival;
//    }
//
//
//
//    public List<String> getRegularPrice(String str) throws ParserConfigurationException, XPathExpressionException {
//
//        TagNode tagNode = new HtmlCleaner().clean(str);
//
//        org.w3c.dom.Document doc;
//        doc = new org.htmlcleaner.DomSerializer(new CleanerProperties()).createDOM(tagNode);
//
//        XPath xpath = XPathFactory.newInstance().newXPath();
//
//        List<String> listOfPrices = new ArrayList<String>();
//        try {
//            //create XPathExpression object
//            XPathExpression expr =
//                    xpath.compile("//div[contains (@class, 'regular-fullPrice')]//span[@class = 'amount']");
//            //evaluate expression result on XML document
//            NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
//            for (int i = 0; i < nodes.getLength(); i++) {
//                listOfPrices.add(nodes.item(i).getFirstChild().getNodeValue());
//            }
//        } catch (XPathExpressionException e) {
//            e.printStackTrace();
//        }
//        return listOfPrices;
//
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
