package com.phototravel;

import com.phototravel.dataCollectors.LuxexpressCollector;
import com.phototravel.dataCollectors.PolskiBusCollector;
import com.phototravel.dataCollectors.Route;
import com.phototravel.dataCollectors.destinations.PolskiBusDestinationsGetter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Olga_Govor on 7/8/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BuscanerApplication.class)
@WebAppConfiguration
public class ConnectionOfCompanies {

    @Autowired
    PolskiBusDestinationsGetter getPolskiBusDestinations;

    @Autowired
    PolskiBusCollector polskiBusCollector;

    @Autowired
    LuxexpressCollector luxexpressCollector;

    @Test
    public void getPriceForDate() throws Exception {

        String d = "13/08/2016";
        String from = "Krakow";
        String to = "Vienna";

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = formatter.parse(d);

        Route route = new Route();
        route.setFrom(from);
        route.setTo(to);
        route.setMinPrice(10000000.0);
        route.setDateOfTrip(date);
//      get companies that run between from and to
//      foreach company {

//         get routedeatils
           route = polskiBusCollector.getPriceForDate(route);
           route = luxexpressCollector.getPriceForDate(route);

//      }
        route.printRouteWithDetails();
    }


}
