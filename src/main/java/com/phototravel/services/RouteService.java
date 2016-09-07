package com.phototravel.services;

import com.phototravel.entity.City;
import com.phototravel.entity.Route;
import com.phototravel.repositories.CompanyRepository;
import com.phototravel.repositories.RouteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
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
        List<Route> routes = new ArrayList<>();

        for (Route route : routeRepository.findAll()) {
            if (route.getFromCityId() == fromCityId
                    && route.getToCityId() == toCityId
                    && route.getCompanyId() == companyId
                    && route.hasChanges() == hasChanges) {
                routes.add(route);
            }
        }
        return routes;
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
}
