package com.phototravel.dataCollectors;

import com.phototravel.dao.TestDao;
import com.phototravel.dataCollectors.destinations.PolskiBusDestinationsGetter;
import com.phototravel.dataCollectors.getDataOfRoute.GetDataPolskiBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Service
public class PolskiBusCollector {

    @Autowired
    PolskiBusDestinationsGetter getPolskiBusDestinations;

    @Autowired
    TestDao testDao;

    public void getPriceForPeriod(Date date1, Date date2) throws Exception {
        GetDataPolskiBus dataPolskiBus = new GetDataPolskiBus();

        //to include in search date2
        Calendar c = Calendar.getInstance();
        c.setTime(date2);
        c.add(Calendar.DATE, 1);
        date2 = c.getTime();

        Date dateOfTrip = date1;
        c.setTime(dateOfTrip);

        String to = "";
        String from = "";

        Map<String, String> listOfDestinations = getPolskiBusDestinations.getDestinations();

        while(dateOfTrip.before(date2)){

            Route route = new Route();
            //just for printing
            route.setFrom("Krakow");
            from = listOfDestinations.get("krak&oacute;w");

            //just for printing
            route.setTo("Vienna");
            to = listOfDestinations.get("wiedeń");

            route.setMinPrice(10000000.0);

            route.setDateOfTrip(dateOfTrip);

            route = dataPolskiBus.getData(route, to, from);
            route.sortByPrice();
            route.printRouteWithDetails();
            testDao.saveRoute(route);

            c.add(Calendar.DATE, 1);
            dateOfTrip = c.getTime();
        }
    }

    public void getPriceForDate(Date date) throws Exception {
        GetDataPolskiBus dataPolskiBus = new GetDataPolskiBus();

        String to = "";
        String from = "";

        Map<String, String> listOfDestinations = getPolskiBusDestinations.getDestinations();

        Route route = new Route();

        //just for printing
        route.setFrom("Krakow");
        from = listOfDestinations.get("krak&oacute;w");

        //just for printing
        route.setTo("Vienna");
        to = listOfDestinations.get("wiedeń");

        route.setMinPrice(10000000.0);
        route.setDateOfTrip(date);

        route = dataPolskiBus.getData(route, to, from);
        route.sortByPrice();
        route.printRouteWithDetails();
        testDao.saveRoute(route);
    }


}
