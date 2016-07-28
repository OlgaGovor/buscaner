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