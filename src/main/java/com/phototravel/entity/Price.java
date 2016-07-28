package com.phototravel.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Time;
import java.util.Date;

/**
 * Created by PBezdienezhnykh on 026 26.7.2016.
 */
@Entity
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "route_id")
    private int routeId;

    @Column(name = "departure_date")
    private Date departureDate;

    @Column(name = "departure_time")
    private Time departureTime;

    @Column(name = "price")
    private double price;

    @Column(name = "last_update")
    private Date lastUpdate;

    public Price(int routeId, Date departureDate, Time departureTime, double price, Date lastUpdate) {
        this.routeId = routeId;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.price = price;
        this.lastUpdate = lastUpdate;
    }

    public Price(){

    }
}
