package com.phototravel;

import com.phototravel.configuration.BuscanerConfiguration;
import com.phototravel.services.Scrapper;
import com.phototravel.services.oneTimeServices.impl.PolskibusCitiesAndRoutesFetcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by Olga_Govor on 6/30/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {BuscanerApplication.class, BuscanerConfiguration.class})
@WebAppConfiguration
public class PolskiTest {

    static final Integer POLSKI_BUS_ID = 1;

    @Autowired
    Scrapper scrapper;

    @Autowired
    PolskibusCitiesAndRoutesFetcher polskibusCitiesAndRoutesFetcher;


    @Test
    public void getPriceForDateAndDirections() throws ParseException {
        String d = "29/08/2016";
        String from = "Krakow";
        String to = "Warszawa";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(d, formatter);

        scrapper.scrapForDay(POLSKI_BUS_ID, from, to, date);
    }

    @Test
    public void getPriceForPeriodAndDirections() throws Exception {
        String d1 = "01/10/2016";
        String d2 = "03/10/2016";
        String from = "Krakow";
        String to = "Warszawa";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date1 = LocalDate.parse(d1, formatter);
        LocalDate date2 = LocalDate.parse(d2, formatter);

        scrapper.scrapForPeriod(POLSKI_BUS_ID, from, to, date1, date2);
    }


    @Test
    //one time per month
    public void getDestinations(){
        polskibusCitiesAndRoutesFetcher.fetchDestinations(POLSKI_BUS_ID);
    }

    @Test
    //one time per change
    public void saveCitiesToDb(){
        polskibusCitiesAndRoutesFetcher.fetchCities();
    }


    @Test
    //one time per month
    public void addRouteToDbFromPolskiBus(){
        polskibusCitiesAndRoutesFetcher.fetchRoutes(POLSKI_BUS_ID);
    }

}
