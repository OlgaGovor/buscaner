package com.phototravel.dataCollectors;

import com.phototravel.dataCollectors.destinations.LuxexpressDestinationGetter;
import com.phototravel.entity.City;
import com.phototravel.repository.CompanyRepository;
import com.phototravel.services.CityService;
import com.phototravel.services.DestinationService;
import org.json.JSONException;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by Olga_Govor on 7/22/2016.
 */

@Service
public class LuxexpressCollector extends BaseCollector {

    @Autowired
    LuxexpressDestinationGetter luxexpressDestinationGetter;

    @Autowired
    DestinationService destinationService;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    CityService cityService;

    public void fillDestinationsForLuxexpress() throws UnsupportedEncodingException, XPathExpressionException, ParserConfigurationException, ParseException, JSONException {

        Map<String, String> destinations = luxexpressDestinationGetter.getDestinations();
        for (Map.Entry<String, String> entry : destinations.entrySet())
        {
            try
            {
                City city = cityService.findCityByName(entry.getKey());
                Integer cityId = city.getCityId();
                Integer companyId = companyRepository.findCompanyByName("Luxexpress");
                System.out.println(companyId+"  "+cityId+"  "+entry.getValue()+"  "+entry.getKey());
                destinationService.createDestination(companyId, cityId, entry.getValue(), entry.getKey());
            }
            catch (Exception e)
            {

            };
        }
    }



}
