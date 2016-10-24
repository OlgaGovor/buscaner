package com.phototravel;

import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by PBezdienezhnykh on 029 29.7.2016.
 */
public class CalendarTest {
    @Test
    public void test() throws ParseException {
        String dateStartStr = "21-07-2016";
        String dateEndStr = "15-09-2016";

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        startDate.setFirstDayOfWeek(Calendar.MONDAY);
        endDate.setFirstDayOfWeek(Calendar.MONDAY);


        startDate.setTime(df.parse(dateStartStr));
        startDate.set(Calendar.DAY_OF_WEEK, startDate.getFirstDayOfWeek());

        endDate.setTime(df.parse(dateEndStr));
        endDate.set(Calendar.DAY_OF_WEEK, startDate.getFirstDayOfWeek());
        endDate.add(Calendar.DATE, 6);


        while (startDate.before(endDate)) {
            System.out.println(df.format(startDate.getTime()));
            startDate.add(Calendar.DATE, 1);
        }
        System.out.println(df.format(endDate.getTime()));


    }
}
