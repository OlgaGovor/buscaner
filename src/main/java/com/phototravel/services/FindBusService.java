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

    public List<Price> findBusForPeriod(RequestForm requestForm) {

        Date endDate =
                requestForm.isScanForPeriod() ? requestForm.getDepartureEndAsDate() : requestForm.getDepartureAsDate();

        int from =requestForm.getFromCity();
        int to = requestForm.getToCity();
        Date d1 = requestForm.getDepartureAsDate();
        Date d2 = endDate;

        List<Price> prices = priceRepository.findCheapestBusByRequestForm(from, to, d1, d2);

        return prices;
    }


    private void findRoute(RequestForm requestForm) {

    }
}
