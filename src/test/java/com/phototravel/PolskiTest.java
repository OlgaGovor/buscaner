package com.phototravel;

import com.phototravel.dataCollectors.PolskiBusCollector;
import com.phototravel.dataCollectors.destinations.PolskiBusDestinationsGetter;
import com.phototravel.entity.City;
import com.phototravel.entity.Price;
import com.phototravel.repository.CountryRepository;
import com.phototravel.repository.PriceRepository;
import com.phototravel.services.CityService;
import com.phototravel.services.RouteService;
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

    @Autowired
    RouteService routeService;

    @Test
    public void putPrice(){
        Price price = new Price(1034, new Date(), new Time(0), new Time(5), 1.21, "zl", new Date());
        priceRepository.save(price);
    }

    @Test
    public void getPriceForDateAndDirections() throws Exception {
        String d = "13/08/2016";
        String from = "Krakow";
        String to = "Warszawa";

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = formatter.parse(d);

        List<Integer> routeIds = routeService.getRouteIdsByCities(from, to);

        for (Integer i: routeIds) {
            polskiBusCollector.getPriceForDateAndSaveToDb(i, date);
        }
    }

    @Test
    public void getPriceForPeriodAndDirections() throws Exception {
        String d1 = "01/08/2016";
        String d2 = "02/08/2016";
        String from = "Krakow";
        String to = "Warszawa";

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = formatter.parse(d1);
        Date date2 = formatter.parse(d2);

        List<Integer> routeIds = routeService.getRouteIdsByCities(from, to);

        for (Integer i: routeIds) {
            polskiBusCollector.getPriceForPeriodAndSaveToDb(i, date1, date2);
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
        for (City c:cityService.findAll()){
            System.out.println(c.getCityName());
        }
    }

//    @Test
//    public void printCitiesWithCountryFromDb(){
//        for (City c:cityService.getAllCitiesWithCountriesIds())
//        {
//
//        }
//    }

    @Test
    //one time per week
    public void addDestinationsToDbFromPolskiBus() throws UnsupportedEncodingException, XPathExpressionException, ParserConfigurationException {
        polskiBusCollector.fillDestinationsForPolskiBus();
    }

    @Test
    //one time per month
    public void addRouteToDbFromPolskiBus() throws UnsupportedEncodingException, XPathExpressionException, ParserConfigurationException, JSONException {
        getPolskiBusDestinations.getRoutesForDb();

    }
}
