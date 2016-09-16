package com.phototravel.controllers.entity;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

/**
 * Created by PBezdienezhnykh on 001 01.9.2016.
 */
public class PriceCalendar {

    private Date date;
    private Double price;
    private String currency;

    private final SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");

    public PriceCalendar(Date date, Double price, String currency) {
        this.date = date;
        this.price = price;
        this.currency = currency;
    }

    public Date getDate() {
        return date;
    }

    public String getDateAsString() {
        return df.format(date);
    }

    public LocalDate getDateAsLocalDate() {
        return date.toLocalDate();
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "PriceCalendar{" +
                "date='" + date + '\'' +
                ", price=" + price +
                '}';
    }
}
