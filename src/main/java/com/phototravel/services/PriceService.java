package com.phototravel.services;

import com.phototravel.entity.Price;
import com.phototravel.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.sql.Time;

/**
 * Created by Olga_Govor on 7/26/2016.
 */
@Service
public class PriceService {

    @Autowired
    PriceRepository priceRepository;

    public void createPrice(Integer routeId, Date departureDate, Time departureTime, Time arrivalTime, double priceNum, String currency, Date lastUpdate) {
        Price price = new Price(routeId, departureDate, departureTime, arrivalTime, priceNum, currency, lastUpdate);
        priceRepository.save(price);
    }
}
