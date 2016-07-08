package com.buscanner.parser;

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
public class MegabusParser extends BaseParser {

    @Override
    public Double parsePrice(String priceStr){
        String [] s = priceStr.trim().substring(0,5).split(",");
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

    @Override
    public List<String> getTimeDeparture(String xPathExpression, String str) throws ParserConfigurationException, XPathExpressionException {

        List<String> listOfDepartures = new ArrayList<String>();

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

                listOfDepartures.add(nodes.item(i).getChildNodes().item(2).getNodeValue().trim());
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        return listOfDepartures;
    }

    @Override
    public List<String> getTimeArrival(String xPathExpression, String str) throws ParserConfigurationException, XPathExpressionException {
        List<String> listOfArrival = new ArrayList<String>();

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
                listOfArrival.add(nodes.item(i).getChildNodes().item(2).getNodeValue().trim());
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        return listOfArrival;
    }


}
