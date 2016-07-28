package com.phototravel.dataCollectors;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by Olga_Govor on 6/29/2016.
 */
public class Route {

    private String toCity;
    private String fromCity;
    private String toDest;
    private String fromDest;
    private Double minPrice;
    private Date dateOfTrip;
    private Date lastUpdateDate;
    private List<RouteDetails> details;

    public Route() {
        this.minPrice = 100000000.0;
    }


    public Route(String to, String from) {
        this.toCity = to;
        this.fromCity = from;
        this.minPrice = 100000000.0;
    }

    public Route(String to, String from, Date date) {
        this.toCity = to;
        this.fromCity = from;
        this.dateOfTrip = date;
        this.minPrice = 100000000.0;
    }


    public List<RouteDetails> getDetails() {
        return details;
    }

    public void setDetails(List<RouteDetails> details) {
        this.details = details;
    }

    public String getToCity() {
        return toCity;
    }

    public String getFromCity() {
        return fromCity;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public Date getDateOfTrip() {
        return dateOfTrip;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }


    public void setToCity(String to) {
        this.toCity = to;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public void setDateOfTrip(Date dateOfTrip) {
        this.dateOfTrip = dateOfTrip;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
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

    public void printRouteWithDetails(){
        System.out.println("From:" + this.getFromCity() + " To:" + this.getToCity() + " Date:" + this.getDateOfTrip() +
                "\nMin.Price = " + this.getMinPrice());
        for (RouteDetails node: this.getDetails()) {
            System.out.println("Price:"+ node.getPrice()+ node.getCurrency() + " Departure:" + node.getTimeDeparture()
                    + " Arrival:" + node.getTimeArrival() + " Company:" + node.getCompanyName() + "");
        }
    }

}
