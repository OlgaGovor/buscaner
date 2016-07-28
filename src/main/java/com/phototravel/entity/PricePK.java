package com.phototravel.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

/**
 * Created by PBezdienezhnykh on 028 28.7.2016.
 */
@Embeddable
public class PricePK implements Serializable {

    @Column(name = "route_id")
    int routeId;

    @Column(name = "departure_date")
    @Temporal(TemporalType.DATE)
    Date departureDate;

    @Column(name = "departure_time")
    Time departureTime;

    public PricePK() {
    }

    public PricePK(int routeId, Date departureDate, Time departureTime) {
        this.routeId = routeId;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
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

    public Time getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Time departureTime) {
        this.departureTime = departureTime;
    }
}
