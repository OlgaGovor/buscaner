package com.phototravel;

import com.phototravel.dataCollectors.PolskiBusCollector;
import com.phototravel.dataCollectors.destinations.PolskiBusDestinationsGetter;
import com.phototravel.iteration.configuration.BuscanerConfiguration;
import com.phototravel.iteration.model.FetcherType;
import com.phototravel.iteration.service.Scrapper;
import com.phototravel.repository.PriceRepository;
import com.phototravel.services.CityService;
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

    @Autowired
    PolskiBusDestinationsGetter getPolskiBusDestinations;

    @Autowired
    PolskiBusCollector polskiBusCollector;

    @Autowired
    CityService cityService;

    @Autowired
    PriceRepository priceRepository;

    @Autowired
    Scrapper scrapper;

    @Test
    public void getPriceForDateAndDirections() throws ParseException {
        String d = "29/08/2016";
        String from = "Krakow";
        String to = "Warszawa";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(d, formatter);

        scrapper.scrapForDay(FetcherType.POLSKI_BUS, from, to, date);
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

        scrapper.scrapForPeriod(FetcherType.POLSKI_BUS, from, to, date1, date2);
    }


//    @Test
//    public void putPrice(){
//        Price price = new Price(1034, new Date(), new Time(0), new Time(5), 1.21, "zl", new Date());
//        priceRepository.save(price);
//    }
//
//    @Test
//    public void getDestinations() throws ParserConfigurationException, XPathExpressionException, UnsupportedEncodingException {
//        Map<String, String> listOfDestinations = getPolskiBusDestinations.getDestinations();
//    }
//
//    @Test
//    //one time per change
//    public void saveCitiesToDb() throws UnsupportedEncodingException, XPathExpressionException, ParserConfigurationException {
//        List<String> listOfCities = getPolskiBusDestinations.getCities();
//        cityService.saveCitiesToDb(listOfCities);
//    }
//
//    @Test
//    public void printCitiesFromDb(){
//        for (City c:cityService.findAll()){
//            System.out.println(c.getCityName());
//        }
//    }
//
//    @Test
//    //one time per week
//    public void addDestinationsToDbFromPolskiBus() throws UnsupportedEncodingException, XPathExpressionException, ParserConfigurationException {
//        polskiBusCollector.fillDestinationsForPolskiBus();
//    }
//
//    @Test
//    //one time per month
//    public void addRouteToDbFromPolskiBus() throws UnsupportedEncodingException, XPathExpressionException, ParserConfigurationException, JSONException {
//        getPolskiBusDestinations.getRoutesForDb();
//    }

}
