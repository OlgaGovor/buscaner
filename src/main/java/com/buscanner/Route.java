package com.buscanner;

import java.util.Date;
import java.util.List;

/**
 * Created by Olga_Govor on 6/29/2016.
 */
public class Route {

    private String to;
    private String from;
    private String minPrice;
    private Date dateOfTrip;
    private Date updated;

    private List<RouteDetails> details;

    public List<RouteDetails> getDetails() {
        return details;
    }

    public void setDetails(List<RouteDetails> details) {
        this.details = details;
    }

    public String getTo() {
        return to;
    }

    public String getFrom() {
        return from;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public Date getDateOfTrip() {
        return dateOfTrip;
    }

    public Date getUpdated() {
        return updated;
    }


    public void setTo(String to) {
        this.to = to;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public void setDateOfTrip(Date dateOfTrip) {
        this.dateOfTrip = dateOfTrip;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }



}
