package com.phototravel.dataCollectors;

import com.phototravel.dao.TestDao;
import com.phototravel.dataCollectors.destinations.PolskiBusDestinationsGetter;
import com.phototravel.dataCollectors.getDataOfRoute.GetDataPolskiBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Service
public class PolskiBusCollector {

    @Autowired
    PolskiBusDestinationsGetter getPolskiBusDestinations;

    @Autowired
    TestDao testDao;

    public void getPrice() throws Exception {
        GetDataPolskiBus dataPolskiBus = new GetDataPolskiBus();
//        BaseSendRequest sendRest = new BaseSendRequest();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date()); // Now use today date.

        String to = "";
        String from = "";

        Map<String, String> listOfDestinations = getPolskiBusDestinations.getDestinations();

//        to = listOfDestinations.get(route.getTo());
//        from = listOfDestinations.get(route.getFrom());


        c.add(Calendar.DATE, 10);
        for(int i=0; i<5 ; i++){

            Route route = new Route();


            //just for printing
            route.setFrom("Krakow");
            from = listOfDestinations.get("krak&oacute;w");


            //just for printing
            route.setTo("Vienna");
            to = listOfDestinations.get("wiedeń");

            route.setMinPrice(10000000.0);


            c.add(Calendar.DATE, 1); // Adding 5 days
            String d = sdf.format(c.getTime());
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date = formatter.parse(d);
            route.setDateOfTrip(date);

            route = dataPolskiBus.getData(route, to, from);
            route.sortByPrice();
            route.printRouteWithDetails();
            testDao.saveRoute(route);
        }
    }


}
