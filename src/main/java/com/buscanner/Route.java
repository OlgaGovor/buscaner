package com.buscanner;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by Olga_Govor on 6/29/2016.
 */
public class Route {

    private String to;
    private String from;
    private Double minPrice;
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

    public Double getMinPrice() {
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

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public void setDateOfTrip(Date dateOfTrip) {
        this.dateOfTrip = dateOfTrip;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }


    public void sortByDeparture(){

        Collections.sort(this.getDetails(), new Comparator<RouteDetails>() {
            @Override
            public int compare(RouteDetails o1, RouteDetails o2) {
                return o1.getTimeDeparture().compareTo(o2.getTimeDeparture());
            }
        });
    }

    public void sortByPrice(){

        Collections.sort(this.getDetails(), new Comparator<RouteDetails>() {
            @Override
            public int compare(RouteDetails o1, RouteDetails o2) {
                return o1.getPrice().compareTo(o2.getPrice());
            }
        });
    }

}
