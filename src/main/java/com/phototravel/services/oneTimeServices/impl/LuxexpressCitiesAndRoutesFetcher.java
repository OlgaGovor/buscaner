package com.phototravel.services.oneTimeServices.impl;

import com.phototravel.entity.City;
import com.phototravel.entity.Destination;
import com.phototravel.services.oneTimeServices.outerRequests.SendRequestLuxexpress;
import com.phototravel.repositories.CompanyRepository;
import com.phototravel.services.CityService;
import com.phototravel.services.DestinationService;
import com.phototravel.services.RouteService;
import com.phototravel.services.oneTimeServices.CitiesAndRoutesFetcher;
import com.sun.jersey.api.client.ClientResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Olga_Govor on 8/18/2016.
 */
@Service
public class LuxexpressCitiesAndRoutesFetcher implements CitiesAndRoutesFetcher {

    private static final String PATH = "https://ticket.luxexpress.eu/en/Stops/GetAllBusStops";
    private static final String CONTENTTYPE = "application/json";
    private static final String PATH_FOR_TIMETABLE = "https://ticket.luxexpress.eu/pl/wyjazdy-harmonogram/";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CityService cityService;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    DestinationService destinationService;

    @Autowired
    RouteService routeService;

    @Override
    public void fetchCities() {
        SendRequestLuxexpress sendRequestLuxexpress = new SendRequestLuxexpress();

        ClientResponse response = sendRequestLuxexpress.sendGetRequest(sendRequestLuxexpress.createWebResource(PATH), CONTENTTYPE);
        String responseStr = sendRequestLuxexpress.getResponseString(response);

        List<String> listOfCities = new ArrayList<String>();

        JSONParser parser = new JSONParser();

        Object obj = null;

        try {
            obj = parser.parse(responseStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        JSONArray array1 = (JSONArray)obj;

        for(int i = 0; i < array1.size(); i++) {
            JSONObject ob = (JSONObject) array1.get(i);
            String city = ob.get("StopName").toString();
            listOfCities.add(city);
            System.out.println(city);
        }

        //Write cities to DB
        cityService.saveCitiesToDb(listOfCities);
    }

    @Override
    public void fetchRoutes(Integer companyId) {

        List<Destination> fromList = destinationService.getDestinationByCompanyId(companyId);
        List<Destination> toList = fromList;


        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 20); //now + 20days
        date = c.getTime();

        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        String dateStr = formatter.format(date);

//        List<Integer> list = new ArrayList<Integer>();
//        for(int i=314; i<555; i++)
//        {
//            list.add(i);
//        }
//        list.add(559);
//        list.add(560);
//        list.add(561);
//        list.add(562);

        SendRequestLuxexpress sendRequestLuxexpress = new SendRequestLuxexpress();
        for (Destination fromDestination : fromList) {
//            Integer dest_id = destinationRepositoty.getDestIdByRequestValue(fromDestination);

//            if (!list.contains(dest_id) ) {

            for (Destination toDestination : toList) {

                String url = PATH_FOR_TIMETABLE + fromDestination.getRequestValue() + "/" + toDestination.getRequestValue() + "?Date=" + dateStr + "&Currency=CURRENCY.PLN";
                logger.info(url);

                    ClientResponse response = sendRequestLuxexpress.sendGetRequest(sendRequestLuxexpress.createWebResource(url), CONTENTTYPE);

                    String responseStr = sendRequestLuxexpress.getResponseString(response);

                    Boolean hasChanges = false;
                    if (responseStr != null) {
                        if (!((responseStr.contains("Prosimy wybrać jako odjazd"))
                                || (responseStr.contains("Aby wyszukać podróż")))) {
                            logger.info("Route exist" + fromDestination + ":" + toDestination);
                            if (responseStr.contains("ico_transfer_route.gif")) {
                                hasChanges = true;
                            }
                            if (responseStr.contains("amount")) {
                                try {
                                    Integer from_dest_id = fromDestination.getDestinationId();
                                    Integer to_dest_id = toDestination.getDestinationId();
                                    Integer from_city_id = fromDestination.getCityId();
                                    Integer to_city_id = toDestination.getCityId();

                                    logger.info("Write toDestination DB  " + from_dest_id + " " + to_dest_id + " " + from_city_id + " " + to_city_id + " " + companyId);

                                    routeService.createRoute(from_dest_id, to_dest_id, from_city_id, to_city_id, companyId, true, hasChanges);
                                } catch (Exception e) {
                                }
                            }
                        }
                    }
                }
//            }
        }
    }

    @Override
    public void fetchDestinations(Integer companyId) {
        SendRequestLuxexpress sendRequestLuxexpress = new SendRequestLuxexpress();

        ClientResponse response = sendRequestLuxexpress.sendGetRequest(sendRequestLuxexpress.createWebResource(PATH), CONTENTTYPE);
        String responseStr = sendRequestLuxexpress.getResponseString(response);

        Map<String, String> listOfDestinations = new LinkedHashMap<String, String>();

        JSONParser parser = new JSONParser();

        Object obj = null;

        try {
            obj = parser.parse(responseStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        JSONArray array1 = (JSONArray)obj;
        for(int i = 0; i < array1.size(); i++) {


            JSONObject ob = (JSONObject) array1.get(i);

            String key = ob.get("StopName").toString();
            String value = ob.get("Slug").toString();
            listOfDestinations.put(key, value);
            System.out.println(key+"  "+value);
        }

        //write Destinations to DB
        for (Map.Entry<String, String> entry : listOfDestinations.entrySet())
        {
            try
            {
                City city = cityService.findCityByName(entry.getKey());
                Integer cityId = city.getCityId();

                System.out.println(companyId+"  "+cityId+"  "+entry.getValue()+"  "+entry.getKey());
                destinationService.createDestination(companyId, cityId, entry.getValue(), entry.getKey());
            }
            catch (Exception e)
            {

            };
        }

    }
}
