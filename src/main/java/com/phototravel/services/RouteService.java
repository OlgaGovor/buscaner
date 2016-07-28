package com.phototravel.services;

import com.phototravel.entity.Route;
import com.phototravel.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Olga_Govor on 7/26/2016.
 */
@Service
public class RouteService {

    @Autowired
    RouteRepository routeRepository;

    public void createRoute(Integer fromDestId, Integer toDestId, Integer fromCityId, Integer toCityId, Integer companyId, Boolean scan) {

        Route route = new Route(fromDestId, toDestId, fromCityId, toCityId, companyId, scan);
        routeRepository.save(route);
    }

}
