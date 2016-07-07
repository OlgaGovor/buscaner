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
public class PolskiBusParser {

    public Double parsePrice(String priceStr){
        String newStr = priceStr.substring(0,5);
        String [] s = newStr.split(",");
        newStr = s[0]+"."+s[1];
        Double price = Double.parseDouble(newStr);
        return price;
    }

    public Time parseTime(String timeStr) throws ParseException {


        DateFormat formatter = new SimpleDateFormat("HH:mm");
        Time time = new java.sql.Time(formatter.parse(timeStr.substring(0,5)).getTime());
        return time;
    }


    public Route parseLuxExpress(String str, Route route) throws XPathExpressionException, ParserConfigurationException, ParseException {

        List<RouteDetails> routeDetailsList = new ArrayList<RouteDetails>();

        List<String> listPrices = getRegularPrice(str);
        List<String> listTimeOfDepartures = getTimeOfDeparture(str);
        List<String> listTimeOfArrival = getTimeOfArrival(str);

        for (int i=0; i<listPrices.size(); i++)
        {
            RouteDetails details = new RouteDetails();

            details.setPrice(parsePrice(listPrices.get(i)));
            Time timeOfDeparture = parseTime(listTimeOfDepartures.get(i));
            details.setTimeDeparture(timeOfDeparture);
            Time timeOfArrival = parseTime(listTimeOfArrival.get(i));
            details.setTimeArrival(timeOfArrival);
            details.setCompany("PolskiBus");

            routeDetailsList.add(details);

        }

        route.setDetails(routeDetailsList);

        return route;
    }

    public List<String> getRegularPrice(String str) throws ParserConfigurationException {

        TagNode tagNode = new HtmlCleaner().clean(str);

        org.w3c.dom.Document doc;
        doc = new org.htmlcleaner.DomSerializer(new CleanerProperties()).createDOM(tagNode);

        XPath xpath = XPathFactory.newInstance().newXPath();

        List<String> listOfPrices = new ArrayList<String>();
        try {
            //create XPathExpression object
            XPathExpression expr =
                    xpath.compile("//div[@class='onb_resultRow']//p[@class='priceHilite']");
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

    public List<String> getTimeOfDeparture(String str) throws ParserConfigurationException {
        List<String> listOfDepartures = new ArrayList<String>();

        TagNode tagNode = new HtmlCleaner().clean(str);

        org.w3c.dom.Document doc;
        doc = new org.htmlcleaner.DomSerializer(new CleanerProperties()).createDOM(tagNode);

        XPath xpath = XPathFactory.newInstance().newXPath();

        try {
            //create XPathExpression object
            XPathExpression expr =
                    xpath.compile("//div[@class='onb_resultRow']//div[@class='onb_col onb_two']//p[position() mod 2 = 1]/b");
            //evaluate expression result on XML document
            NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < nodes.getLength(); i++) {
                listOfDepartures.add(nodes.item(i).getFirstChild().getNodeValue().substring(11).trim());
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        return listOfDepartures;
    }

    public List<String> getTimeOfArrival(String str) throws ParserConfigurationException {
        List<String> listOfArrival = new ArrayList<String>();


        TagNode tagNode = new HtmlCleaner().clean(str);

        org.w3c.dom.Document doc;
        doc = new org.htmlcleaner.DomSerializer(new CleanerProperties()).createDOM(tagNode);

        XPath xpath = XPathFactory.newInstance().newXPath();

        try {
            //create XPathExpression object
            XPathExpression expr =
                    xpath.compile("//div[@class='onb_resultRow']//div[@class='onb_col onb_two']//p[position() mod 2 = 0]/b");
            //evaluate expression result on XML document
            NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < nodes.getLength(); i++) {

                listOfArrival.add(nodes.item(i).getFirstChild().getNodeValue().substring(11).trim());
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        return listOfArrival;
    }

}
