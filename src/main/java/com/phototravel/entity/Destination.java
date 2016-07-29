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

    public int getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(int destinationId) {
        this.destinationId = destinationId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getRequestValue() {
        return requestValue;
    }

    public void setRequestValue(String requestValue) {
        this.requestValue = requestValue;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

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

    public Destination(){}
}
