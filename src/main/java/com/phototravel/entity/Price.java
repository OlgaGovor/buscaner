package com.phototravel.entity;


import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by PBezdienezhnykh on 026 26.7.2016.
 */
@Entity
public class Price {

    @EmbeddedId
    private PricePK id;

//    @Column(name = "route_id")
//    private int routeId;
//
//    @Column(name = "departure_date")
//    @Temporal(TemporalType.DATE)
//    private Date departureDate;
//
//    @Column(name = "departure_time")
//    private Time departureTime;

    @Column(name = "arrival_time")
    private Time arrivalTime;

    @Column(name = "price")
    private double price;

    @Column(name = "currency")
    private String currency;

    @Column(name = "last_update")
    private Date lastUpdate;

    @Transient
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    @Transient
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");
    @Transient
    private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Price(int routeId, Date departureDate, Time departureTime, Time arrivalTime,
                 double price, String currency, Date lastUpdate) {
        this.id = new PricePK();
        this.id.routeId = routeId;
        this.id.departureDate = departureDate;
        this.id.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.price = price;
        this.currency = currency;
        this.lastUpdate = lastUpdate;
    }

    public Price() {
        this.id = new PricePK();
    }

    public int getRouteId() {
        return id.routeId;
    }

    public void setRouteId(int routeId) {
        this.id.routeId = routeId;
    }

    public String getDepartureDateString() {
        return DATE_FORMAT.format(id.departureDate);
    }

    public Date getRawDepartureDate() {
        return id.departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.id.departureDate = departureDate;
    }

    public String getDepartureTime() {

        return TIME_FORMAT.format(id.departureTime);
    }

    public void setDepartureTime(Time departureTime) {
        this.id.departureTime = departureTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public String getLastUpdateString() {

        return DATE_TIME_FORMAT.format(lastUpdate);
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public PricePK getId() {
        return id;
    }

    public void setId(PricePK id) {
        this.id = id;
    }

    public String getArrivalTime() {
        return TIME_FORMAT.format(arrivalTime);
    }

    public void setArrivalTime(Time arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "Price{" +
                "id=" + id +
                ", arrivalTime=" + arrivalTime +
                ", price=" + price +
                ", currency='" + currency + '\'' +

                '}';
    }
}
