package com.phototravel;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by PBezdienezhnykh on 029 29.7.2016.
 */
public class CalendarTest {
    @Test
    public void test() {
        Calendar cal = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("EEE dd/MM/yyyy");


        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(2016, 8 - 1, 5);
        System.out.println(df.format(cal.getTime()));
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());


        System.out.println(df.format(cal.getTime()));

    }
}
