package com.phototravel.dataCollectors;

import com.phototravel.repository.CompanyRepository;
import com.phototravel.services.CityService;
import com.phototravel.services.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.UnsupportedEncodingException;

@Service
public class PolskiBusCollector extends BaseCollector {

//    @Autowired
//    PolskiBusDestinationsGetter polskiBusDestinationsGetter;

    @Autowired
    CityService cityService;

    @Autowired
    DestinationService destinationService;

    @Autowired
    CompanyRepository companyRepository;

    public void fillDestinationsForPolskiBus() throws UnsupportedEncodingException, XPathExpressionException, ParserConfigurationException {

//        Map <String, String> destinations = polskiBusDestinationsGetter.getDestinations();
//        for (Map.Entry<String, String> entry : destinations.entrySet())
//        {
//            try
//            {
//                City city = cityService.findCityByName(entry.getKey());
//                Integer cityId = city.getCityId();
//                Integer companyId = companyRepository.findCompanyByName("Luxexpress");
//                System.out.println("1  "+cityId+"  "+entry.getValue()+"  "+entry.getKey());
//                destinationService.createDestination(companyId, cityId, entry.getValue(), entry.getKey());
//            }
//            catch (Exception e)
//            {
//
//            };
//        }
    }


}
