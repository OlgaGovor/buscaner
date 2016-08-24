package com.phototravel.services;

import com.phototravel.entity.Route;
import com.phototravel.repositories.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Created by PBezdienezhnykh on 024 24.8.2016.
 */
@Service
public class ScheduledService {

    @Autowired
    RouteRepository routeRepository;

    @Autowired
    Scrapper scrapper;

    @Value("${scrapper.scanPeriod}")
    private Integer scanPeriod;

    @Scheduled(cron = "0 */5 * * * *")
    public void runNightScan() {

        System.out.println("runNightScan");

    }


    private void scanAllRoutes() {

        Iterable<Route> routes = routeRepository.findAll();

        for (Route route : routes) {
            route.getCompanyId();
        }


    }
}
