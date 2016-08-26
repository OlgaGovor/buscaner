package com.phototravel.services;

import com.phototravel.entity.City;
import com.phototravel.entity.Route;
import com.phototravel.repositories.CompanyRepository;
import com.phototravel.repositories.RouteRepository;
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

    @Autowired
    CompanyRepository companyRepository;

    public void createRoute(Integer fromDestId, Integer toDestId, Integer fromCityId, Integer toCityId, Integer companyId, Boolean scan, Boolean hasChanges) {

        Route route = new Route(fromDestId, toDestId, fromCityId, toCityId, companyId, scan, hasChanges);
        routeRepository.save(route);
    }

//    public List<Integer> getRouteIdsByCities(String from, String to){
//        City fromCityObj = cityService.findCityByName(from);
//        Integer fromCityId = fromCityObj.getCityId();
//        City toCityObj = cityService.findCityByName(to);
//        Integer toCityId = toCityObj.getCityId();
//
//        List<Integer> routeIds = routeRepository.getRouteIdByCityId(fromCityId, toCityId);
//        return  routeIds;
//    }

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


        List<Integer> routeIds = routeRepository.getRouteIdByCityIdAndCompanyId(fromCityId, toCityId, companyId);
        return  routeIds;
    }

    public List<Integer> getRouteIdsByCitiesIdsAndCompany(int fromCityId, int toCityId, Integer companyId) {

        List<Integer> routeIds = routeRepository.getRouteIdByCityIdAndCompanyId(fromCityId, toCityId, companyId);
        return  routeIds;
    }

}
