package com.phototravel.dataCollectors.parser;


import com.phototravel.entity.Price;
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
import java.util.Date;
import java.util.List;

/**
 * Created by Olga_Govor on 7/1/2016.
 */
abstract public class BaseParser {

    public List<Price> parse(String str, Integer routeId, Date date, String xPathPrice, String xPathDeparture, String xPathArrival, String currency) throws Exception {

        List<String> listPrices = getRegularPrice(xPathPrice, str);
        List<String> listTimeOfDepartures = getTimeDeparture(xPathDeparture, str);
        List<String> listTimeOfArrival = getTimeArrival(xPathArrival, str);

        List<Price> listOfPriceEntity = new ArrayList<Price>();

        for (int i = 0; i < listPrices.size(); i++) {
            Price priceEntity = new Price();

            priceEntity.setRouteId(routeId);
            priceEntity.setCurrency(currency);

            Double priceFromRequest = parsePrice(listPrices.get(i));
            priceEntity.setPrice(priceFromRequest);

            priceEntity.setDepartureDate(date);

            Time timeOfDeparture = parseTime(listTimeOfDepartures.get(i));
            priceEntity.setDepartureTime(timeOfDeparture);

            Time timeOfArrival = parseTime(listTimeOfArrival.get(i));
            priceEntity.setArrivalTime(timeOfArrival);

            priceEntity.setLastUpdate(new Date());

            listOfPriceEntity.add(priceEntity);
        }
        return listOfPriceEntity;
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

    public Double parsePrice(String priceStr) {

        Double price = Double.parseDouble(priceStr);
        return price;
    }

    public Time parseTime(String timeStr) throws ParseException {

        DateFormat formatter = new SimpleDateFormat("HH:mm");
        Time price = new Time(formatter.parse(timeStr).getTime());
        return price;
    }

}
