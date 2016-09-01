package com.phototravel.controllers.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by PBezdienezhnykh on 001 01.9.2016.
 */
public class PriceCalendar {

    private Date date;
    private Double price;

    private final SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");

    public PriceCalendar(Date date, Double price) {
        this.date = date;
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public String getDateAsString() {
        return df.format(date);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "PriceCalendar{" +
                "date='" + date + '\'' +
                ", price=" + price +
                '}';
    }
}
