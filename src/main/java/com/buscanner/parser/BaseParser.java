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

        List<String> listPrices = getRegularPrice(xPathPrice, str);
        List<String> listTimeOfDepartures = getTimeDeparture(xPathDeparture, str);
        List<String> listTimeOfArrival = getTimeArrival(xPathArrival, str);

        Double minPrice = 100000000.0;
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

            if (minPrice > price){
                minPrice = price;
            }
        }
        route.setDetails(routeDetailsList);
        route.setMinPrice(minPrice);
        return route;
    }


    public List<String> getTimeDeparture(String xPathExpression, String str) throws ParserConfigurationException, XPathExpressionException {
        return parseResponseString(xPathExpression, str);
    }

    public List<String> getTimeArrival(String xPathExpression, String str) throws ParserConfigurationException, XPathExpressionException {
        return parseResponseString(xPathExpression, str);
    }

    public List<String> getRegularPrice(String xPathExpression, String str) throws ParserConfigurationException, XPathExpressionException {
        return parseResponseString(xPathExpression, str);
    }

    public List<String> getPromoPrice(String xPathExpression, String str) throws ParserConfigurationException, XPathExpressionException {
        return parseResponseString(xPathExpression, str);
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

}
