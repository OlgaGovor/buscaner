package com.phototravel.dataCollectors;

import com.phototravel.dao.TestDao;
import com.phototravel.dataCollectors.destinations.PolskiBusDestinationsGetter;
import com.phototravel.dataCollectors.getDataOfRoute.GetDataPolskiBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PolskiBusCollector extends BaseCollector {

    @Autowired
    PolskiBusDestinationsGetter getPolskiBusDestinations;

    @Autowired
    TestDao testDao;

    public Route getPriceForDate(Route route) throws Exception {

        GetDataPolskiBus dataPolskiBus = new GetDataPolskiBus();

        Map<String, String> listOfDestinations = getPolskiBusDestinations.getDestinations();

//        Route route = new Route();

        //just for printing
//        route.setFrom(from);
        //get destination for FROM for current company using route.getFrom()
        String from = listOfDestinations.get("krak&oacute;w");

        //just for printing
//        route.setTo(to);
        //get destination for TO for current company using route.getTo()
        String to = listOfDestinations.get("wiedeń");

        route = dataPolskiBus.getData(route, to, from);
        route.setLastUpdateDate(new Date());

        route.sortByPrice();
        testDao.saveRoute(route);

        return route;
    }


}
