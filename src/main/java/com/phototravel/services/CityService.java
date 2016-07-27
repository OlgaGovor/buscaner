package com.phototravel.services;

import com.phototravel.entity.City;
import com.phototravel.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by PBezdienezhnykh on 026 26.7.2016.
 */
@Service
public class CityService {

    @Autowired
    CityRepository cityRepository;

    public void createCity(String name, Integer countryId) {

        City city = new City(name, countryId);
        try {
            cityRepository.save(city);
        }
        catch (DataIntegrityViolationException e)
        {}
    }

    public void saveCitiesToDb(List<String> listOfCities)
    {
        for (String city: listOfCities) {
            createCity(city, 1);
        }
    }




}
