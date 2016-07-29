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
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        String dateStart = "28-07-2016";
        String dateEnd = "15-09-2016";

        cal.setTime(df.parse(dateStart));
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());



        System.out.println(df.format(cal.getTime()));


        System.out.println(df.format(cal.getTime()));

    }
}
