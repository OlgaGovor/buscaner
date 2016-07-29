package com.phototravel.services;

import com.phototravel.entity.City;
import com.phototravel.entity.Route;
import com.phototravel.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void createRoute(Integer fromDestId, Integer toDestId, Integer fromCityId, Integer toCityId, Integer companyId, Boolean scan) {

        Route route = new Route(fromDestId, toDestId, fromCityId, toCityId, companyId, scan);
        routeRepository.save(route);
    }

    public List<Integer> getRouteIdsByCities(String from, String to){
        City fromCityObj = cityService.findCityByName(from);
        Integer fromCityId = fromCityObj.getCityId();
        City toCityObj = cityService.findCityByName(to);
        Integer toCityId = toCityObj.getCityId();

        List<Integer> routeIds = routeRepository.getRouteIdByCityId(fromCityId, toCityId);
        return  routeIds;
    }

}
