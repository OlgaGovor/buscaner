package com.phototravel.services;

import com.phototravel.entity.City;
import com.phototravel.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<String> getAllCities(){

        List<String> listOfCityNames = new ArrayList<String>();

        Iterable<City> cities =  cityRepository.findAll();
        for (City city:cities) {
            listOfCityNames.add(city.getCityName());
        }
        return listOfCityNames;
    }

    public List<City> getAllCitiesWithCountriesIds(){

        List<City> listOfCities = new ArrayList<City>();

        Iterable<City> cities =  cityRepository.findAll();
        for (City city:cities) {
            listOfCities.add(city);
        }
        return listOfCities;
    }


    @Cacheable("city")
    private Map<Integer, City> getAllCitiesMap() {
        Map<Integer, City> citiesMap = new HashMap<>();
        Iterable<City> cities = cityRepository.findAll();
        for (City city : cities) {
            citiesMap.put(city.getCityId(), city);
        }
        return citiesMap;
    }

    public List<City> findAll() {
        return new ArrayList(getAllCitiesMap().values());
    }


    public City findOne(Integer id) {
        return getAllCitiesMap().get(id);
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
