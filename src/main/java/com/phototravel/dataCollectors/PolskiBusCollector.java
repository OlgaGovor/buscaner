package com.phototravel.dataCollectors;

import com.phototravel.dataCollectors.destinations.PolskiBusDestinationsGetter;
import com.phototravel.dataCollectors.getDataOfRoute.GetDataPolskiBus;
import com.phototravel.repository.CityRepository;
import com.phototravel.repository.CompanyRepository;
import com.phototravel.repository.DestinationRepositoty;
import com.phototravel.repository.RouteRepository;
import com.phototravel.services.DestinationService;
import com.phototravel.services.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.UnsupportedEncodingException;
import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class PolskiBusCollector extends BaseCollector {

    @Autowired
    PolskiBusDestinationsGetter getPolskiBusDestinations;

    @Autowired
    PriceService priceService;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    DestinationRepositoty destinationRepositoty;

    @Autowired
    DestinationService destinationService;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    RouteRepository routeRepository;

    public Route getPriceForDate(Route route) throws Exception {

        GetDataPolskiBus dataPolskiBus = new GetDataPolskiBus();

        //get destination for FROM for current company using route.getFromCity()
        String fromCity = route.getFromCity();
        String toCity = route.getToCity();

        //get cityIds
        Integer fromCityId = cityRepository.findCityByName(fromCity);
        Integer toCityId = cityRepository.findCityByName(toCity);

        //get request Values for CityId
        Integer companyId = companyRepository.findCompanyByName("PolskiBus");
        List<String> fromRequestValue = destinationRepositoty.getRequestValuesByCompanyIdAndCityId(companyId, fromCityId);
        List<String> toRequestValue = destinationRepositoty.getRequestValuesByCompanyIdAndCityId(companyId, toCityId);

        for (String from: fromRequestValue) {
            for (String to: toRequestValue) {
                route = dataPolskiBus.getData(route, to, from);
            }
        }

        route.setLastUpdateDate(new Date());

        route.sortByPrice();

        //get routeId from DB
        //need to refactor for Destination not for city
        Integer routeId = routeRepository.getRouteIdByCityId(fromCityId, toCityId);

        //need to refactor real time
        Time time = new Time(0);
        //save price

        priceService.createPrice(routeId, route.getDateOfTrip(), time, route.getMinPrice(), route.getLastUpdateDate());

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
