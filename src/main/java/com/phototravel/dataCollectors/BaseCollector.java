package com.phototravel.dataCollectors;

import com.phototravel.dataCollectors.getDataOfRoute.GetDataPolskiBus;
import com.phototravel.entity.Price;
import com.phototravel.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Olga_Govor on 7/22/2016.
 */
@Service
public class BaseCollector {

    @Autowired
    PriceRepository priceRepository;

    @Autowired
    GetDataPolskiBus getData;

    public void getPriceForPeriodAndSaveToDb(Integer routeId, Date date1, Date date2) throws Exception {

        //to include in search date2
        Calendar c = Calendar.getInstance();
        c.setTime(date2);
        c.add(Calendar.DATE, 1);
        date2 = c.getTime();

        Date dateOfTrip = date1;
        c.setTime(dateOfTrip);

        while(dateOfTrip.before(date2)){

            getPriceForDateAndSaveToDb(routeId,dateOfTrip);
            c.add(Calendar.DATE, 1);
            dateOfTrip = c.getTime();
        }
    }

    public void getPriceForDateAndSaveToDb(Integer routeId, Date date) throws Exception {

        List<Price> prices = getData.getData(routeId, date);

        for (Price p:prices) {
            priceRepository.save(p);
            System.out.println(p.toString());
        }
    }


}
