package com.phototravel.services.impl.luxExpress;

import com.phototravel.entity.Route;

import java.time.LocalDate;

/**
 * Created by comp on 14.09.2016.
 */
public interface Task extends Runnable {

    void setRoute(Route route);

    void setDate(LocalDate date);


}
