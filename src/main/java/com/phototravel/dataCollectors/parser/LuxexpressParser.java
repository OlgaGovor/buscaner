package com.phototravel.dataCollectors.parser;

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
        Time time = new Time(formatter.parse(timeStr).getTime());
        return time;
    }

}
