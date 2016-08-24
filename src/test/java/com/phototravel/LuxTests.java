package com.phototravel;

import com.phototravel.services.Scrapper;
import com.phototravel.services.oneTimeServices.impl.LuxexpressCitiesAndRoutesFetcher;
import org.json.JSONException;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by Olga_Govor on 6/29/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BuscanerApplication.class)
@WebAppConfiguration
public class LuxTests {

    static final Integer LUX_EXPRESS_ID = 2;

    @Autowired
    Scrapper scrapper;

    @Autowired
    LuxexpressCitiesAndRoutesFetcher luxexpressCitiesAndRoutesFetcher;

    @Test
    public void getPriceForDateAndDirections() throws java.text.ParseException {
        String d = "27/09/2016";
        String from = "Krakow";
        String to = "Vienna";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(d, formatter);

        scrapper.scrapForDay(LUX_EXPRESS_ID, from, to, date);
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

        scrapper.scrapForPeriod(LUX_EXPRESS_ID, from, to, date1, date2);
    }


//    @Test
//    public void getDestinations() throws ParserConfigurationException, XPathExpressionException, JSONException, ParseException {
//
//        Map<String, String> listOfDestinations = luxexpressDestinationGetter.getDestinations();
//    }
//
    @Test
    //one time per change
    public void saveCitiesToDb() throws UnsupportedEncodingException, XPathExpressionException, ParserConfigurationException, ParseException, JSONException {
        luxexpressCitiesAndRoutesFetcher.fetchCities();
    }

    @Test
    //one time per week
    public void addDestinationsToDbFromLuxexpress() throws UnsupportedEncodingException, XPathExpressionException, ParserConfigurationException, ParseException, JSONException {
        luxexpressCitiesAndRoutesFetcher.fetchDestinations(LUX_EXPRESS_ID);
    }

    @Test
    //one time per month
    public void addRouteToDbFromLuxexpress() throws UnsupportedEncodingException, XPathExpressionException, ParserConfigurationException, JSONException {
//        luxexpressDestinationGetter.getRoutesForDb();
        luxexpressCitiesAndRoutesFetcher.fetchRoutes(LUX_EXPRESS_ID);
    }

}
