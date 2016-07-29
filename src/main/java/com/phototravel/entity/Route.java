package com.phototravel.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by PBezdienezhnykh on 026 26.7.2016.
 */
@Entity
public class Route {

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public int getFromDestinationId() {
        return fromDestinationId;
    }

    public void setFromDestinationId(int fromDestinationId) {
        this.fromDestinationId = fromDestinationId;
    }

    public int getToDestinationId() {
        return toDestinationId;
    }

    public void setToDestinationId(int toDestinationId) {
        this.toDestinationId = toDestinationId;
    }

    public int getFromCityId() {
        return fromCityId;
    }

    public void setFromCityId(int fromCityId) {
        this.fromCityId = fromCityId;
    }

    public int getToCityId() {
        return toCityId;
    }

    public void setToCityId(int toCityId) {
        this.toCityId = toCityId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "route_id")
    private int routeId;

    @Column(name = "from_destination_id")
    private int fromDestinationId;

    @Column(name = "to_destination_id")
    private int toDestinationId;

    @Column(name = "from_city_id")
    private int fromCityId;

    @Column(name = "to_city_id")
    private int toCityId;

    @Column(name = "company_id")
    private int companyId;

    @Column(name = "is_active")
    private boolean active;

    public Route(int fromDestinationId, int toDestinationId, int fromCityId, int toCityId, int companyId, boolean active) {
        this.fromDestinationId = fromDestinationId;
        this.toDestinationId = toDestinationId;
        this.fromCityId = fromCityId;
        this.toCityId = toCityId;
        this.companyId = companyId;
        this.active = active;
    }

}
