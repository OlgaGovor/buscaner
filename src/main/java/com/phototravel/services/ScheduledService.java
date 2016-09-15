package com.phototravel.services;

import com.phototravel.entity.Route;
import com.phototravel.repositories.RouteRepository;
import com.phototravel.services.dbWriter.ScannerMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    ScannerMonitor scannerMonitor;

    @Scheduled(cron = "* */30 * * * *")
    public void runNightScan() {
        logger.info("........................runScheduledScan........................");
        if (!scannerMonitor.isScanInProgress()) {
            logger.info("........................runScan..................................");
            scanAllRoutes();
        } else {
            logger.info("........................SkipScan - in progress...................");
        }
        logger.info("........................endScheduledScan........................");
    }


    private void scanAllRoutes() {

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(scanPeriod);

        List<LocalDate> dates = new ArrayList<>();
        while (startDate.isBefore(endDate.plusDays(1))) {
            dates.add(startDate);
            startDate = startDate.plusDays(1);
        }


        Iterable<Route> routes = routeRepository.findAll();
        List<Route> someRoutes = new ArrayList<>();

        Integer i = 0;
        for (Route route : routes) {
            if (route.getCompanyId() == 2) {
                someRoutes.add(route);
                i++;
                if (i > 40) {
                    break;
                }
            }

        }


        scrapper.scrap(someRoutes, dates);


    }
}
