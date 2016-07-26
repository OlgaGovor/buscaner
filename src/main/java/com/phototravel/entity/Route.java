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

    @Column(name = "company_id")
    private int companyId;

    @Column(name = "is_active")
    private boolean active;

}
