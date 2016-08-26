package com.phototravel.services;

import com.phototravel.entity.Route;
import com.phototravel.repositories.RouteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * Created by PBezdienezhnykh on 024 24.8.2016.
 */
@Service
public class ScheduledService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RouteRepository routeRepository;

    @Autowired
    Scrapper scrapper;

    @Value("${scrapper.scanPeriod}")
    private Integer scanPeriod;

    @Scheduled(cron = "*/10 * * * * *")
    public void runNightScan() {
        logger.info("runNightScan........................");
        scanAllRoutes();
        logger.info("endNightScan........................");
    }


    private void scanAllRoutes() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(scanPeriod);


        Iterable<Route> routes = routeRepository.findAll();

        for (Route route : routes) {
            logger.info("run scrapRouteForPeriod " + route + " " + startDate + " " + endDate);
            scrapper.scrapRouteForPeriod(route, startDate, endDate);
        }


    }
}
