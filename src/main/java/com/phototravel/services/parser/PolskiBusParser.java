package com.phototravel.services.parser;

import org.springframework.stereotype.Component;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class PolskiBusParser extends BaseResponseParser {

    @Override
    public Double parsePrice(String priceStr){
        priceStr = priceStr.trim();
        String newStr = priceStr.substring(0,priceStr.indexOf('z'));
        String [] s = newStr.split(",");
        newStr = s[0]+"."+s[1];
        Double price = Double.parseDouble(newStr);
        return price;
    }

    @Override
    public Time parseTime(String timeStr) throws ParseException {

        String newTimeStr = timeStr.substring(11).trim();
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        Time time = new Time(formatter.parse(newTimeStr.substring(0,5)).getTime());
        return time;
    }

    @Override
    public String parseDuration(String dur) throws ParseException {
        dur = dur.trim();
        Integer ind = dur.indexOf("hrs");
        dur = dur.substring(0,ind)+':'+dur.substring(ind+4,ind+6);
        return dur;
    }

    @Override
    public List<String> getDuration(String xPathExpression, String str) throws ParserConfigurationException, XPathExpressionException {
        List<String> hourAndMin = parseResponseString(xPathExpression, str);
        List<String> resultTime = new ArrayList<String>();
        for (int i=0; i<hourAndMin.size(); i=i+2) {
            //04hrs 40min
            String result = hourAndMin.get(i).trim()+ " " +hourAndMin.get(i+1).trim();
            resultTime.add(result);
        }
        return resultTime;
    }

}
