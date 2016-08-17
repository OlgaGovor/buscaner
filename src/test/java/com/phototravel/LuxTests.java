package com.phototravel;

import com.phototravel.dataCollectors.LuxexpressCollector;
import com.phototravel.dataCollectors.destinations.LuxexpressDestinationGetter;
import com.phototravel.iteration.model.FetcherType;
import com.phototravel.iteration.service.Scrapper;
import com.phototravel.services.CityService;
import com.phototravel.services.RouteService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by Olga_Govor on 6/29/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BuscanerApplication.class)
@WebAppConfiguration
public class LuxTests {

    @Autowired
    LuxexpressCollector luxexpressCollector;

    @Autowired
    LuxexpressDestinationGetter luxexpressDestinationGetter;

    @Autowired
    RouteService routeService;

    @Autowired
    CityService cityService;

    @Autowired
    Scrapper scrapper;

    @Test
    public void getPriceForDateAndDirections() throws java.text.ParseException {
        String d = "17/09/2016";
        String from = "Krakow";
        String to = "Vienna";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(d, formatter);

        scrapper.scrapForDay(FetcherType.LUX_EXPRESS, from, to, date);
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

        scrapper.scrapForPeriod(FetcherType.LUX_EXPRESS, from, to, date1, date2);
    }


//    @Test
//    public void getDestinations() throws ParserConfigurationException, XPathExpressionException, JSONException, ParseException {
//
//        Map<String, String> listOfDestinations = luxexpressDestinationGetter.getDestinations();
//    }
//
//    @Test
//    //one time per change
//    public void saveCitiesToDb() throws UnsupportedEncodingException, XPathExpressionException, ParserConfigurationException, ParseException, JSONException {
//        List<String> listOfCities = luxexpressDestinationGetter.getCities();
//        cityService.saveCitiesToDb(listOfCities);
//    }
//
//    @Test
//    //one time per week
//    public void addDestinationsToDbFromPolskiBus() throws UnsupportedEncodingException, XPathExpressionException, ParserConfigurationException, ParseException, JSONException {
//        luxexpressCollector.fillDestinationsForLuxexpress();
//    }
//
//    @Test
//    //one time per month
//    public void addRouteToDbFromPolskiBus() throws UnsupportedEncodingException, XPathExpressionException, ParserConfigurationException, JSONException {
//        luxexpressDestinationGetter.getRoutesForDb();
//
//    }

}
