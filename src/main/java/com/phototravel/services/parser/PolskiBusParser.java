package com.phototravel.services.parser;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Olga_Govor on 7/1/2016.
 */
public class PolskiBusParser extends BaseParser {

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

}
