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
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "company_id")
    private int companyId;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_urlid")
    private String companyUrl;

    public Company(String companyName, String companyUrl) {
        this.companyName = companyName;
        this.companyUrl = companyUrl;
    }
}
