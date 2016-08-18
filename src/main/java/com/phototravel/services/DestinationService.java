package com.phototravel.services;

import com.phototravel.entity.Destination;
import com.phototravel.repositories.DestinationRepositoty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Olga_Govor on 7/26/2016.
 */
@Service
public class DestinationService {

    @Autowired
    DestinationRepositoty destinationRepositoty;

    public void createDestination(Integer companyId, Integer cityId, String requestValue, String destinationName) {
        Destination destination = new Destination(companyId, cityId, requestValue, destinationName);
        destinationRepositoty.save(destination);
    }
}
