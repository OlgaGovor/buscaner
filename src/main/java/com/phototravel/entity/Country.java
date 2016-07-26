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
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "country_id")
    private int cityId;

    @Column(name = "country_name")
    private String cityName;

    public Country(String cityName) {
        this.cityName = cityName;
    }
}
