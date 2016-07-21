package com.phototravel.dataCollectors;

import java.sql.Time;

/**
 * Created by Olga_Govor on 6/29/2016.
 */
public class RouteDetails {
//Comment test
    private String companyName;
    private Time timeDeparture;
    private Time timeArrival;
    private Double price;
    private String currency;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Time getTimeDeparture() {
        return timeDeparture;
    }

    public void setTimeDeparture(Time timeDeparture) {
        this.timeDeparture = timeDeparture;
    }

    public Time getTimeArrival() {
        return timeArrival;
    }

    public void setTimeArrival(Time timeArrival) {
        this.timeArrival = timeArrival;
    }

}
