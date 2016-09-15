package com.phototravel.services;

import com.phototravel.entity.City;
import com.phototravel.entity.Route;
import com.phototravel.repositories.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olga_Govor on 7/26/2016.
 */
@Service
public class RouteService {

    @Autowired
    RouteRepository routeRepository;

    @Autowired
    CityService cityService;

    public void createRoute(Integer fromDestId, Integer toDestId, Integer fromCityId, Integer toCityId, Integer companyId, Boolean scan, Boolean hasChanges) {

        Route route = new Route(fromDestId, toDestId, fromCityId, toCityId, companyId, scan, hasChanges);
        routeRepository.save(route);
    }


    public Route getRouteByRouteId(Integer routeId) {
        return routeRepository.findRouteByRouteId(routeId);
    }


    public List<Route> getRoutesByCitiesIdsAndCompany(int fromCityId, int toCityId, Integer companyId, Boolean hasChanges) {
        return routeRepository.getRoutesByCityIdAndCompanyId(fromCityId, toCityId, companyId, hasChanges);

    }


    public List<City> findRouteCities(Integer cityId, String depDst) {

        List<City> cities = new ArrayList<>();

        List<Integer> cityIds = new ArrayList<>();
        if (depDst.equalsIgnoreCase("dep")) {
            cityIds = routeRepository.findByFromCityId(cityId);
            for (Integer toCityId : cityIds) {
                City city = cityService.findOne(toCityId);
                if (city != null) {
                    cities.add(city);
                }
            }

        } else if (depDst.equalsIgnoreCase("dst")) {
            cityIds = routeRepository.findByToCityId(cityId);
            for (Integer fromCityId : cityIds) {
                City city = cityService.findOne(fromCityId);
                if (city != null) {
                    cities.add(city);
                }

            }

        }

        return cities;
    }

    public List<Integer> findRouteIdsByCityIds(int fromCityId, int toCityId, Boolean hasChanges) {
        return routeRepository.findByCityIds(fromCityId, toCityId, hasChanges);
    }

    public List<Route> findRoutesByCityIds(Integer fromCityId, Integer toCityId, Boolean hasChanges) {
        return routeRepository.findRoutesByCityIds(fromCityId, toCityId, hasChanges);
    }
}
