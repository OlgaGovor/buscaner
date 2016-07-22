package com.phototravel.dataCollectors;

import com.phototravel.dao.TestDao;
import com.phototravel.dataCollectors.getDataOfRoute.GetDataLuxexpress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by Olga_Govor on 7/22/2016.
 */

@Service
public class LuxexpressCollector extends BaseCollector {

    @Autowired
    TestDao testDao;


    public Route getPriceForDate(Route route) throws Exception {

        GetDataLuxexpress dataLuxexpress = new GetDataLuxexpress();

        //get destination for FROM for current company using route.getFrom()
        String from = "krakow";

        //get destination for TO for current company using route.getTo()
        String to = "vienna-stadion-center";

        route = dataLuxexpress.getData(route, to, from);
        route.setLastUpdateDate(new Date());

        route.sortByPrice();
        testDao.saveRoute(route);

        return route;
    }


}
