package com.phototravel.services.impl;

import com.phototravel.BuscanerTestApplication;
import com.phototravel.entity.Price;
import com.phototravel.entity.Route;
import com.phototravel.repositories.PriceRepository;
import com.phototravel.repositories.RouteRepository;
import com.phototravel.services.PriceService;
import com.phototravel.services.RouteService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BuscanerTestApplication.class)
@WebAppConfiguration
public class ScrapperImplTest {

    @Autowired
    ScrapperImpl scrapper;

    @Autowired
    RouteService routeService;

    @Autowired
    RouteRepository routeRepository;

    @Autowired
    PriceRepository priceRepository;


    @Test
    public void testScrapRouteForDateM() throws Exception {
        PriceService.date_format = ":departureDate";

        //Route route, LocalDate date
        List<Route> routes = routeService.getRoutesByCitiesIdsAndCompany(3, 231, 1, false);
        String d = "15/10/2016";


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(d, formatter);
        for (Route route1 : routes) {
            scrapper.scrapRouteForDateM(route1, date);
            scrapper.scrapRouteForDateM(route1, date);
            scrapper.scrapRouteForDateM(route1, date);
            scrapper.scrapRouteForDateM(route1, date);
        }


        Iterable<Price> prices = priceRepository.findAll();

        for (Price price : prices) {
            System.out.println(price);

        }


    }
}