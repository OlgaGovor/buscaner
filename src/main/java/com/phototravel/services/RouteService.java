package com.phototravel.services;

import com.phototravel.entity.City;
import com.phototravel.entity.Route;
import com.phototravel.repositories.CompanyRepository;
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

    @Autowired
    CompanyRepository companyRepository;

    public void createRoute(Integer fromDestId, Integer toDestId, Integer fromCityId, Integer toCityId, Integer companyId, Boolean scan, Boolean hasChanges) {

        Route route = new Route(fromDestId, toDestId, fromCityId, toCityId, companyId, scan, hasChanges);
        routeRepository.save(route);
    }


    public Route getRouteByRouteId(Integer routeId) {
        for (Route route : routeRepository.findAll()) {
            if (route.getRouteId() == routeId) {
                return route;
            }
        }
        return null;
    }


    public List<Integer> getRouteIdsByCitiesAndCompany(String from, String to, Integer companyId) {
        City fromCityObj = cityService.findCityByName(from);
        if (fromCityObj == null) {
            throw new IllegalArgumentException("No FROM City found in city table - " + from);
        }
        Integer fromCityId = fromCityObj.getCityId();
        City toCityObj = cityService.findCityByName(to);
        if (toCityObj == null) {
            throw new IllegalArgumentException("No TO City found in city table - " + to);
        }
        Integer toCityId = toCityObj.getCityId();


        return getRouteIdsByCitiesIdsAndCompany(fromCityId, toCityId, companyId);
    }

    public List<Integer> getRouteIdsByCitiesIdsAndCompany(int fromCityId, int toCityId, Integer companyId) {
        List<Integer> routeIds = new ArrayList<>();

        for (Route route : routeRepository.findAll()) {
            if (route.getFromCityId() == fromCityId
                    && route.getToCityId() == toCityId
                    && route.getCompanyId() == companyId) {
                routeIds.add(route.getRouteId());
            }
        }
        return routeIds;
    }


}
