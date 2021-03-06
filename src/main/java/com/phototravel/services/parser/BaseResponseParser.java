package com.phototravel.services.parser;


import com.phototravel.entity.Price;
import com.phototravel.services.companiesConfig.Config;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.w3c.dom.NodeList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Olga_Govor on 7/1/2016.
 */
public abstract class BaseResponseParser {

    public List<Price> parse(String str, Integer routeId, LocalDate date, Config config) throws Exception {

        List<String> listPrices = getRegularPrice(config.getParam("XPATH_PRICE"), str);
        List<String> listTimeOfDepartures = getTimeDeparture(config.getParam("XPATH_DEPARTURE"), str);
        List<String> listTimeOfArrival = getTimeArrival(config.getParam("XPATH_ARRIVAL"), str);
        List<String> listDuration = getDuration(config.getParam("XPATH_DURATION"), str);

        List<Price> listOfPriceEntity = new ArrayList<Price>();

        for (int i = 0; i < listPrices.size(); i++) {
            Price priceEntity = new Price();

            priceEntity.setRouteId(routeId);
            priceEntity.setCurrency(config.getParam("CURRENCY"));

            Double priceFromRequest = parsePrice(listPrices.get(i));
            priceEntity.setPrice(priceFromRequest);

            Date d = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
            priceEntity.setDepartureDate(d);

            Time timeOfDeparture = parseTime(listTimeOfDepartures.get(i));
            priceEntity.setDepartureTime(timeOfDeparture);

            Time timeOfArrival = parseTime(listTimeOfArrival.get(i));
            priceEntity.setArrivalTime(timeOfArrival);

            priceEntity.setLastUpdate(new Date());

            String duration = parseDuration(listDuration.get(i));
            priceEntity.setDuration(duration);

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

    public List<String> getDuration(String xPathExpression, String str) throws ParserConfigurationException, XPathExpressionException {
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

    public String parseDuration(String dur) throws ParseException {
        return dur.trim();
    }

}
