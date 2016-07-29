package com.phototravel.controllers.entity;

import java.text.SimpleDateFormat;

/**
 * Created by PBezdienezhnykh on 029 29.7.2016.
 */
public class CalendarView {

    private String fromDate;
    private String toDate;

    private final SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");

    public CalendarView(String fromDate, String toDate) {
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public CalendarView() {
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

   /* public Date getFirstDayOfMonth(){
        Date date = df.parse(fromDate);

    }*/
}
