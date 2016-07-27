package com.phototravel.services;

import com.phototravel.controllers.entity.RequestForm;
import com.phototravel.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by PBezdienezhnykh on 026 26.7.2016.
 */
@Service
public class FindBusService {

    @Autowired
    PriceRepository priceRepository;


    public Double findBus(RequestForm requestForm) {
        System.out.println("findChipestBusByRequestForm="
                + requestForm.getFromCity() + " "
                + requestForm.getToCity() + " "
                + requestForm.getDepartureAsDate());

        //priceRepository.test(requestForm.getDepartureAsDate());

        Double price = priceRepository.findChipestBusByRequestForm(requestForm.getFromCity(), requestForm.getToCity(),
                requestForm.getDepartureAsDate());
        return price;

    }

    private void findRoute(RequestForm requestForm) {

    }
}
