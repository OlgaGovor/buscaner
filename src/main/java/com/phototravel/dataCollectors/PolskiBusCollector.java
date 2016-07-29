package com.phototravel.dataCollectors;

import com.phototravel.dataCollectors.destinations.PolskiBusDestinationsGetter;
import com.phototravel.entity.City;
import com.phototravel.repository.CompanyRepository;
import com.phototravel.repository.DestinationRepositoty;
import com.phototravel.repository.RouteRepository;
import com.phototravel.services.CityService;
import com.phototravel.services.DestinationService;
import com.phototravel.services.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

@Service
public class PolskiBusCollector extends BaseCollector {

    @Autowired
    PolskiBusDestinationsGetter getPolskiBusDestinations;

    @Autowired
    PriceService priceService;

    @Autowired
    CityService cityService;

    @Autowired
    DestinationRepositoty destinationRepositoty;

    @Autowired
    DestinationService destinationService;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    RouteRepository routeRepository;

    public void fillDestinationsForPolskiBus() throws UnsupportedEncodingException, XPathExpressionException, ParserConfigurationException {

        Map <String, String> destinations = getPolskiBusDestinations.getDestinations();
        for (Map.Entry<String, String> entry : destinations.entrySet())
        {
            try
            {
                City city = cityService.findCityByName(entry.getKey());
                Integer cityId = city.getCityId();
                System.out.println("1  "+cityId+"  "+entry.getValue()+"  "+entry.getKey());
                destinationService.createDestination(1, cityId, entry.getValue(), entry.getKey());
            }
            catch (Exception e)
            {

            };
        }
    }


}
