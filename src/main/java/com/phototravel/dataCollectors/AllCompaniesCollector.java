package com.phototravel.dataCollectors;

import com.phototravel.dao.TestDao;
import com.phototravel.dataCollectors.destinations.PolskiBusDestinationsGetter;
import com.phototravel.dataCollectors.getDataOfRoute.GetDataLuxexpress;
import com.phototravel.dataCollectors.getDataOfRoute.GetDataPolskiBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * Created by Olga_Govor on 7/22/2016.
 */
@Service
public class AllCompaniesCollector extends BaseCollector
{
    @Autowired
    TestDao testDao;

    @Autowired
    PolskiBusDestinationsGetter getPolskiBusDestinations;

    public Route getPriceForDate(Route route) throws Exception {

        GetDataLuxexpress dataLuxexpress = new GetDataLuxexpress();
        GetDataPolskiBus dataPolskiBus = new GetDataPolskiBus();
        String to = "";
        String from = "";

//      get companies that provided this route
//        if (luxExpress == true)
//        {
//        get destination for FROM for current company using route.getFromCity()
        from = "krakow";
//        get destination for TO for current company using route.getToCity()
        to = "vienna-stadion-center";

        // route = dataLuxexpress.getData(route, to, from);
//        }
//        if (polskiBus == true)
//        {
        Map<String, String> listOfDestinations = getPolskiBusDestinations.getDestinations();
//        get destination for FROM for current company using route.getFromCity()
        from = listOfDestinations.get("krak&oacute;w");
//        get destination for TO for current company using route.getToCity()
        to = listOfDestinations.get("wiedeń");

        // route = dataPolskiBus.getData(route, to, from);
//        }


        route.setLastUpdateDate(new Date());
        route.sortByPrice();
        testDao.saveRoute(route);

        return route;
    }

}
