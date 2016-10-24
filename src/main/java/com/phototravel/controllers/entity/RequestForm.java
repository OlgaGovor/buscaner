package com.phototravel.controllers.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by PBezdienezhnykh on 026 26.7.2016.
 */
public class RequestForm {

    private int fromCity;
    private int toCity;
    private boolean scanForPeriod;
    private String departureDate;
    private String departureDateEnd;
    private String currency = "EUR";

    private final SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public int getFromCity() {
        return fromCity;
    }

    public void setFromCity(int fromCity) {
        this.fromCity = fromCity;
    }

    public int getToCity() {
        return toCity;
    }

    public void setToCity(int toCity) {
        this.toCity = toCity;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public Date getDepartureAsDate() {
        if (departureDate != null) {
            try {
                return df.parse(departureDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public LocalDate getDepartureDateAsLocalDate() {
        return LocalDate.parse(departureDate, formatter);
    }

    public LocalDate getDepartureDateEndAsLocalDate() {
        return LocalDate.parse(departureDateEnd, formatter);
    }

    public Date getDepartureEndAsDate() {
        if (departureDateEnd != null && !departureDateEnd.isEmpty()) {
            try {
                return df.parse(departureDateEnd);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = formatter.format(departureDate);
    }


    public boolean isScanForPeriod() {
        return scanForPeriod;
    }

    public void setScanForPeriod(boolean scanForPeriod) {
        this.scanForPeriod = scanForPeriod;
    }

    public String getDepartureDateEnd() {
        return departureDateEnd;
    }

    public void setDepartureDateEnd(String departureDateEnd) {
        this.departureDateEnd = departureDateEnd;
    }

    public void setDepartureDateEnd(LocalDate departureDateEnd) {
        this.departureDateEnd = formatter.format(departureDateEnd);
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "RequestForm{" +
                "fromCity=" + fromCity +
                ", toCity=" + toCity +
                ", scanForPeriod=" + scanForPeriod +
                ", departureDate='" + departureDate + '\'' +
                ", departureDateEnd='" + departureDateEnd + '\'' +
                ", currency='" + currency + '\'' +
                '}';
    }
}
