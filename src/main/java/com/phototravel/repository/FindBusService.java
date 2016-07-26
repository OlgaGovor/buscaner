package com.phototravel.repository;

import com.phototravel.controllers.entity.RequestForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by PBezdienezhnykh on 026 26.7.2016.
 */
@Service
public class FindBusService {

    @Autowired
    PriceRepository priceRepository;


    public double findBus(RequestForm requestForm) {
        return priceRepository.findChipestBusByRequestForm(requestForm.getFromCity(), requestForm.getToCity(),
                requestForm.getDepartureAsDate());

    }

    private void findRoute(RequestForm requestForm) {

    }
}
