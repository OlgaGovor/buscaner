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
public class Destination {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "destination_id")
    private int destinationId;

    @Column(name = "company_id")
    private int companyId;

    @Column(name = "city_id")
    private int cityId;

    @Column(name = "request_value")
    private String requestValue;

    @Column(name = "destination_name")
    private String destinationName;

    public Destination(int companyId, int cityId, String requestValue, String destinationName) {
        this.companyId = companyId;
        this.cityId = cityId;
        this.requestValue = requestValue;
        this.destinationName = destinationName;
    }
}
