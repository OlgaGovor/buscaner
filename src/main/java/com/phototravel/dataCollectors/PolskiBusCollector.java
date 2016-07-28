package com.phototravel.dataCollectors;

import com.phototravel.dao.TestDao;
import com.phototravel.dataCollectors.destinations.PolskiBusDestinationsGetter;
import com.phototravel.dataCollectors.getDataOfRoute.GetDataPolskiBus;
import com.phototravel.repository.CityRepository;
import com.phototravel.services.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Service
public class PolskiBusCollector extends BaseCollector {

    @Autowired
    PolskiBusDestinationsGetter getPolskiBusDestinations;

    @Autowired
    TestDao testDao;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    DestinationService destinationService;

    public Route getPriceForDate(Route route) throws Exception {

        GetDataPolskiBus dataPolskiBus = new GetDataPolskiBus();

        Map<String, String> listOfDestinations = getPolskiBusDestinations.getDestinations();

//        Route route = new Route();

        //just for printing
//        route.setFromCity(from);
        //get destination for FROM for current company using route.getFromCity()
        String from = listOfDestinations.get("krak&oacute;w");

        //just for printing
//        route.setToCity(to);
        //get destination for TO for current company using route.getToCity()
        String to = listOfDestinations.get("wiedeń");

        route = dataPolskiBus.getData(route, to, from);
        route.setLastUpdateDate(new Date());

        route.sortByPrice();
        testDao.saveRoute(route);

        return route;
    }


    public void fillDestinationsForPolskiBus() throws UnsupportedEncodingException, XPathExpressionException, ParserConfigurationException {

        Map <String, String> destinations = getPolskiBusDestinations.getDestinations();
        for (Map.Entry<String, String> entry : destinations.entrySet())
        {
            try
            {
                Integer cityId = cityRepository.findCityByName(entry.getKey());
                System.out.println("1  "+cityId+"  "+entry.getValue()+"  "+entry.getKey());
                destinationService.createDestination(1, cityId, entry.getValue(), entry.getKey());
            }
            catch (Exception e)
            {

            };
        }
    }


}
