package com.phototravel;

import com.phototravel.dataCollectors.Route;
import com.phototravel.dataCollectors.destinations.PolskiBusDestinationsGetter;
import com.phototravel.dataCollectors.getDataOfRoute.GetDataLuxexpress;
import com.phototravel.dataCollectors.getDataOfRoute.GetDataPolskiBus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created by Olga_Govor on 7/8/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BuscanerApplication.class)
@WebAppConfiguration
public class ConnectionOfCompanies {

    @Autowired
    PolskiBusDestinationsGetter getPolskiBusDestinations;

    @Test
    public void getPrice() throws Exception {
        GetDataPolskiBus dataPolskiBus = new GetDataPolskiBus();
        GetDataLuxexpress dataLuxexpress = new GetDataLuxexpress();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date()); // Now use today date.

        String to = "";
        String from = "";


        Map<String, String> listOfDestinations = getPolskiBusDestinations.getDestinations();

//        Get destinations from list
//        to = listOfDestinations.get(route.getTo());
//        from = listOfDestinations.get(route.getFrom());


        c.add(Calendar.DATE, 3); //start from today+10days
        for(int i=0; i<10 ; i++){
            //new date new route
            Route route = new Route();
            route.setFrom("Krakow");
            route.setTo("Vienna");
            route.setMinPrice(10000000.0);


            c.add(Calendar.DATE, 1); // Adding 1 day
            String d = sdf.format(c.getTime());
            Date date = sdf.parse(d);
            route.setDateOfTrip(date);

            to = listOfDestinations.get("wiedeń");
            from = listOfDestinations.get("krak&oacute;w");
            route = dataPolskiBus.getData(route, to, from);

            to = "krakow";
            from = "vienna-stadion-center";
            route = dataLuxexpress.getData(route, to, from);

            System.out.println("BY DEPARTURE");
            route.sortByDeparture();
            route.printRouteWithDetails();

            System.out.println("BY PRICE");
            route.sortByPrice();
            route.printRouteWithDetails();
        }



    }

}
