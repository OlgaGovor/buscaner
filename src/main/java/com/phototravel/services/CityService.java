package com.phototravel.services;

import com.phototravel.entity.City;
import com.phototravel.repositories.CityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by PBezdienezhnykh on 026 26.7.2016.
 */
@Service
public class CityService {

    @Autowired
    CityRepository cityRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void createCity(String name, Integer countryId) {

        City city = new City(name, countryId);
        try {
            cityRepository.save(city);
        } catch (DataIntegrityViolationException e) {
        }
    }

    public void saveCitiesToDb(List<String> listOfCities) {
        for (String city : listOfCities) {
            createCity(city, 1);
        }
    }


    private Map<Integer, City> getAllCitiesMap() {
        logger.info("getAllCitiesMap");
        Map<Integer, City> citiesMap = new HashMap<>();
        Iterable<City> cities = cityRepository.findAll();
        for (City city : cities) {
            citiesMap.put(city.getCityId(), city);
        }
        logger.info("getAllCitiesMap - done");
        return citiesMap;
    }


    public List<City> findAll() {
        return cityRepository.findAll();
    }


    public City findOne(Integer id) {
        List<City> cities = cityRepository.findAll();
        for (City city : cities) {
            if (city.getCityId() == id) {
                return city;
            }
        }
        return null;
    }

    public City findCityByName(String cityName) {
        if (cityName == null || cityName.isEmpty()) {
            return null;
        }
        List<City> cities = findAll();
        for (City city : cities) {
            if (cityName.equals(city.getCityName())) {
                return city;
            }
        }
        return null;
    }


}
