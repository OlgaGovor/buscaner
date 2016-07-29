package com.phototravel.services;

import com.phototravel.controllers.entity.RequestForm;
import com.phototravel.entity.Price;
import com.phototravel.repository.PriceRepository;
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

        List<Price> prices = priceRepository.findCheapestBusByRequestForm(requestForm.getFromCity(), requestForm.getToCity(),
                requestForm.getDepartureAsDate(), endDate);
        return prices;

    }

    private void findRoute(RequestForm requestForm) {

    }
}
