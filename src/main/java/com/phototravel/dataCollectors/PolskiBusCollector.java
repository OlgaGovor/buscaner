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

    public void getPriceForPeriod(String from, String to, Date date1, Date date2) throws Exception {
        GetDataPolskiBus dataPolskiBus = new GetDataPolskiBus();

        //to include in search date2
        Calendar c = Calendar.getInstance();
        c.setTime(date2);
        c.add(Calendar.DATE, 1);
        date2 = c.getTime();

        Date dateOfTrip = date1;
        c.setTime(dateOfTrip);

        Map<String, String> listOfDestinations = getPolskiBusDestinations.getDestinations();

        while(dateOfTrip.before(date2)){

            //check in DB and print
            //if updated date more than 2 days ago send request

            getPriceForDate(from, to, dateOfTrip);

            c.add(Calendar.DATE, 1);
            dateOfTrip = c.getTime();
        }
    }

    public void getPriceForDate(String from, String to, Date date) throws Exception {
        GetDataPolskiBus dataPolskiBus = new GetDataPolskiBus();

        Map<String, String> listOfDestinations = getPolskiBusDestinations.getDestinations();

        Route route = new Route();

        //just for printing
        route.setFrom(from);
        //get destination for FROM for current company
        from = listOfDestinations.get("krak&oacute;w");

        //just for printing
        route.setTo(to);
        //get destination for TO for current company
        to = listOfDestinations.get("wiedeń");

        route.setMinPrice(10000000.0);
        route.setDateOfTrip(date);
        route.setLastUpdateDate(new Date());

        route = dataPolskiBus.getData(route, to, from);
        route.sortByPrice();
        route.printRouteWithDetails();
        testDao.saveRoute(route);
    }


}
