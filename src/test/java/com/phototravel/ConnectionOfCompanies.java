package com.phototravel;

import com.phototravel.dataCollectors.AllCompaniesCollector;
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
    AllCompaniesCollector allCompaniesCollector;

    @Test
    public void getPriceForDate() throws Exception {

        String d = "26/08/2016";
        String from = "Krakow";
        String to = "Praga";

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = formatter.parse(d);

        Route route = new Route(from, to, date);
        route = allCompaniesCollector.getPriceForDate(route);

        route.printRouteWithDetails();
    }

    @Test
    public void getPriceForPeriod() throws Exception {

        String d1 = "17/08/2016";
        String d2 = "19/08/2016";
        String from = "Krakow";
        String to = "Vienna";

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = formatter.parse(d1);
        Date date2 = formatter.parse(d2);

        Route route = new Route(from, to);

//        List<Route> routeList = allCompaniesCollector.getPriceForPeriodAndSaveToDb(route, date1, date2);
//        for (Route r: routeList) {
//            r.printRouteWithDetails();
//        }
    }


}
