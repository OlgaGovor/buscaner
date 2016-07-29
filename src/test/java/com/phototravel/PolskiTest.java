package com.phototravel;

import com.phototravel.dataCollectors.PolskiBusCollector;
import com.phototravel.dataCollectors.Route;
import com.phototravel.dataCollectors.destinations.PolskiBusDestinationsGetter;
import com.phototravel.entity.City;
import com.phototravel.entity.Price;
import com.phototravel.repository.CountryRepository;
import com.phototravel.repository.PriceRepository;
import com.phototravel.services.CityService;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.UnsupportedEncodingException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Olga_Govor on 6/30/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BuscanerApplication.class)
@WebAppConfiguration
public class PolskiTest {

    @Autowired
    PolskiBusDestinationsGetter getPolskiBusDestinations;

    @Autowired
    PolskiBusCollector polskiBusCollector;

    @Autowired
    CityService cityService;

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    PriceRepository priceRepository;

    @Test
    public void putPrice(){
        Price price = new Price(1034, new Date(), new Time(0), 1.21, new Date());
        priceRepository.save(price);
    }

    @Test
    public void getPriceForDateAndDirections() throws Exception {
        String d = "13/08/2016";
        String from = "Krakow";
        String to = "Warszawa";

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = formatter.parse(d);

        Route route = new Route(from, to, date);
        route = polskiBusCollector.getPriceForDate(route);

        route.printRouteWithDetails();
    }

    @Test
    public void getPriceForPeriodAndDirections() throws Exception {
        String d1 = "13/08/2016";
        String d2 = "27/08/2016";
        String from = "Krakow";
        String to = "Warszawa";

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = formatter.parse(d1);
        Date date2 = formatter.parse(d2);

        Route route = new Route(from, to);
        List<Route> routeList = polskiBusCollector.getPriceForPeriod(route, date1, date2);
        for (Route r: routeList) {
            r.printRouteWithDetails();
        }
    }

    @Test
    public void getDestinations() throws ParserConfigurationException, XPathExpressionException, UnsupportedEncodingException {

        Map<String, String> listOfDestinations = getPolskiBusDestinations.getDestinations();

    }

    @Test
    //one time per change
    public void saveCitiesToDb() throws UnsupportedEncodingException, XPathExpressionException, ParserConfigurationException {
        List<String> listOfCities = getPolskiBusDestinations.getCities();
        cityService.saveCitiesToDb(listOfCities);

    }

    @Test
    public void printCitiesFromDb(){
        for (String c:cityService.getAllCities()){
            System.out.println(c);
        }
    }

    @Test
    public void printCitiesWithCountryFromDb(){
        for (City c:cityService.getAllCitiesWithCountriesIds())
        {

        }
    }

    @Test
    //one time per week
    public void addRoutesToDbFromPolskiBus() throws UnsupportedEncodingException, XPathExpressionException, ParserConfigurationException {
        polskiBusCollector.fillDestinationsForPolskiBus();
    }

    @Test
    //one time per month
    public void addRouteToDbFromPolskiBus() throws UnsupportedEncodingException, XPathExpressionException, ParserConfigurationException, JSONException {
        getPolskiBusDestinations.getRoutesForDb();

    }
}
