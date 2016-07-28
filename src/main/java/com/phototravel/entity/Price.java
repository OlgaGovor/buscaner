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

    /*@Column(name = "route_id")
    private int routeId;


    @Column(name = "departure_date")
    @Temporal(TemporalType.DATE)
    private Date departureDate;

    @Column(name = "departure_time")
    private Time departureTime;*/

    @Column(name = "price")
    private double price;

    @Column(name = "last_update")
    private Date lastUpdate;

    @Transient
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
    @Transient
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("hh:mm");
    @Transient
    private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

    public Price(int routeId, Date departureDate, Time departureTime, double price, Date lastUpdate) {
        this.id = new PricePK();
        this.id.routeId = routeId;
        this.id.departureDate = departureDate;
        this.id.departureTime = departureTime;
        this.price = price;
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

    public String getDepartureDate() {
        return DATE_FORMAT.format(id.departureDate);
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

    public String getLastUpdate() {

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


}