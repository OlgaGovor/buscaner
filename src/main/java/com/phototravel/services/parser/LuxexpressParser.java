package com.phototravel.services.parser;

import org.springframework.stereotype.Component;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public class LuxexpressParser extends BaseResponseParser {

    @Override
    public Double parsePrice(String priceStr) {
        String[] s = priceStr.split(",");
        String newStr = s[0] + "." + s[1];
        Double price = Double.parseDouble(newStr);
        return price;
    }

    @Override
    public Time parseTime(String timeStr) throws ParseException {

        DateFormat formatter = new SimpleDateFormat("HH:mm");
        Time time = new Time(formatter.parse(timeStr).getTime());
        return time;
    }

    @Override
    public String parseDuration(String dur) throws ParseException {
        dur = dur.trim();
        Integer dIndex = dur.indexOf('d');
        Integer hIndex = dur.indexOf('h');
        Integer mIndex = dur.indexOf('h');
        if (dIndex > 0) {
            dur = "24:00";
        } else {
            dur = dur.substring(0, hIndex) + ':' + dur.substring(hIndex + 2, hIndex + 4);
        }
        return dur;
    }


}
