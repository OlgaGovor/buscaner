package com.phototravel.dataCollectors;

import com.phototravel.dao.TestDao;
import com.phototravel.dataCollectors.destinations.PolskiBusDestinationsGetter;
import com.phototravel.dataCollectors.getDataOfRoute.GetDataPolskiBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PolskiBusCollector {

    @Autowired
    PolskiBusDestinationsGetter getPolskiBusDestinations;

    @Autowired
    TestDao testDao;

    public List<Route> getPriceForPeriod(Route route, Date date1, Date date2) throws Exception {
        List<Route> routeList = new ArrayList<>();

        //to include in search date2
        Calendar c = Calendar.getInstance();
        c.setTime(date2);
        c.add(Calendar.DATE, 1);
        date2 = c.getTime();

        Date dateOfTrip = date1;
        c.setTime(dateOfTrip);

        while(dateOfTrip.before(date2)){

            //check in DB and print
            //if updated date more than 2 days ago send request
//            Route route = new Route();
//            route.setFrom(from);
//            route.setTo(to);
//            route.setDateOfTrip(dateOfTrip);
//            Date d = testDao.getUpdateDateForRoute(route);
//            if (d > two_days_ago)
            Route newRoute = new Route();
            newRoute.setFrom(route.getFrom());
            newRoute.setTo(route.getTo());
            newRoute.setMinPrice(route.getMinPrice());
            newRoute.setDateOfTrip(dateOfTrip);
            routeList.add(getPriceForDate(newRoute));
//            else
//            use old data

            c.add(Calendar.DATE, 1);
            dateOfTrip = c.getTime();
        }

        return routeList;
    }

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
