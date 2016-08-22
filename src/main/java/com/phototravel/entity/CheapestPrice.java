package com.phototravel.entity;

import java.util.Date;

/**
 * Created by Olga_Govor on 8/22/2016.
 */
public class CheapestPrice {

    private double price;
    private int routeId;
    private Date departureDate;


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

}
