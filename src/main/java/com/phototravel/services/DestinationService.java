package com.phototravel.services;

import com.phototravel.entity.Destination;
import com.phototravel.repositories.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olga_Govor on 7/26/2016.
 */
@Service
public class DestinationService {

    @Autowired
    DestinationRepository destinationRepository;

    public void createDestination(Integer companyId, Integer cityId, String requestValue, String destinationName) {
        Destination destination = new Destination(companyId, cityId, requestValue, destinationName);
        destinationRepository.save(destination);
    }


    public String getDestinationNameByDestinationId(int destinationId) {
        for (Destination destination : destinationRepository.findAll()) {
            if (destination.getDestinationId() == destinationId) {
                return destination.getDestinationName();
            }
        }

        return null;
    }

    public List<Destination> getDestinationByCompanyId(int companyId) {
        List<Destination> destinations = new ArrayList<>();

        for (Destination destination : destinationRepository.findAll()) {
            if (destination.getCompanyId() == companyId) {
                destinations.add(destination);
            }
        }
        return destinations;
    }

    public Destination getDestinationByRequestValue(String requestValue) {
        for (Destination destination : destinationRepository.findAll()) {
            if (destination.getRequestValue().equalsIgnoreCase(requestValue)) {
                return destination;
            }
        }

        return null;
    }
}
