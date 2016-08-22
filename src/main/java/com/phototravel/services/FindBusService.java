package com.phototravel.services;

import com.phototravel.controllers.entity.RequestForm;
import com.phototravel.entity.Price;
import com.phototravel.repositories.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by PBezdienezhnykh on 026 26.7.2016.
 */
@Service
public class FindBusService {

    @Autowired
    PriceRepository priceRepository;


    public List<Price> findBus(RequestForm requestForm) {
        //System.out.println("findBus=" + requestForm);

        Date endDate =
                requestForm.isScanForPeriod() ? requestForm.getDepartureEndAsDate() : requestForm.getDepartureAsDate();

        List<Price> prices = priceRepository.findBusByRequestForm(requestForm.getFromCity(), requestForm.getToCity(),
                requestForm.getDepartureAsDate(), endDate);
        return prices;

    }

    public void findBusForPeriod(RequestForm requestForm) {

        Date endDate =
                requestForm.isScanForPeriod() ? requestForm.getDepartureEndAsDate() : requestForm.getDepartureAsDate();

        int from =requestForm.getFromCity();
        int to = requestForm.getToCity();
        Date d1 = requestForm.getDepartureAsDate();
        Date d2 = endDate;

        List<Object[]> prices = priceRepository.findCheapestBusByRequestForm(from, to, d1, d2);

        for (Object[] o:prices) {
            Object p = o[0];
            Object d = o[1];
            Object pp = o[2];
            System.out.println(p+" "+d+" "+pp);
        }





    }


    private void findRoute(RequestForm requestForm) {

    }
}
