package com.phototravel.dataCollectors.destinations;

import com.phototravel.RequestSender;
import com.phototravel.repositories.CompanyRepository;
import com.phototravel.repositories.DestinationRepositoty;
import com.phototravel.services.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Olga_Govor on 7/22/2016.
 */
@Service
public class LuxexpressDestinationGetter {

//get json with all bus stops
    //StopId
    //StopName
    //Slug  - key for request
    private static final String PATH = "http://ticket.luxexpress.eu/en/Stops/GetAllBusStops";

    private static final String CONTENTTYPE = "application/json";

//    public Map<String, String> getDestinations() throws ParserConfigurationException, XPathExpressionException, ParseException, JSONException {
//
//        SendRequestLuxexpress sendRequestLuxexpress = new SendRequestLuxexpress();
//
//        ClientResponse response = sendRequestLuxexpress.sendGetRequest(sendRequestLuxexpress.createWebResource(PATH), CONTENTTYPE);
//        String responseStr = sendRequestLuxexpress.getResponseString(response);
//
//        Map <String, String> listOfDestinations = new LinkedHashMap<String, String>();
//
//        JSONParser parser = new JSONParser();
//
//        Object obj = null;
//
//        obj = parser.parse(responseStr);
//
//
//
//        JSONArray array1 = (JSONArray)obj;
//
//
//        for(int i = 0; i < array1.size(); i++) {
//
//
//            JSONObject ob = (JSONObject) array1.get(i);
//
//            String key = ob.get("StopName").toString();
//            String value = ob.get("Slug").toString();
//            listOfDestinations.put(key, value);
//            System.out.println(key+"  "+value);
//        }
//
//            System.out.println();
//
//        return listOfDestinations;
//    }
//
//    public List<String> getCities() throws ParserConfigurationException, XPathExpressionException, ParseException, JSONException {
//
//        SendRequestLuxexpress sendRequestLuxexpress = new SendRequestLuxexpress();
//
//        ClientResponse response = sendRequestLuxexpress.sendGetRequest(sendRequestLuxexpress.createWebResource(PATH), CONTENTTYPE);
//        String responseStr = sendRequestLuxexpress.getResponseString(response);
//
//        List <String> listOfCities = new ArrayList<String>();
//
//        JSONParser parser = new JSONParser();
//
//        Object obj = null;
//
//        obj = parser.parse(responseStr);
//
//        JSONArray array1 = (JSONArray)obj;
//
//
//        for(int i = 0; i < array1.size(); i++) {
//
//
//            JSONObject ob = (JSONObject) array1.get(i);
//
//            String city = ob.get("StopName").toString();
//            listOfCities.add(city);
//            System.out.println(city);
//        }
//
//        System.out.println();
//
//        return listOfCities;
//    }

    @Autowired
    RequestSender requestSender;

    @Autowired
    DestinationRepositoty destinationRepositoty;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    RouteService routeService;

    private static final String PATHLUX = "https://ticket.luxexpress.eu/pl/wyjazdy-harmonogram/";

//    public void getRoutesForDb() throws ParserConfigurationException, XPathExpressionException, UnsupportedEncodingException, JSONException {
//
//        Integer companyId = companyRepository.findCompanyByName("Luxexpress");
//
//
//        List<String> fromList = destinationRepositoty.getRequestValuesByCompanyId(companyId);
//        List<String> toList = fromList;
//
//
//        Date date = new Date();
//        Calendar c = Calendar.getInstance();
//        c.setTime(date);
//        c.add(Calendar.DATE, 20); //now + 20days
//        date = c.getTime();
//
//        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
//        String dateStr = formatter.format(date);
//
//        List<Integer> list = new ArrayList<Integer>();
//        for(int i=314; i<555; i++)
//        {
//            list.add(i);
//        }
//        list.add(559);
//        list.add(560);
//        list.add(561);
//        list.add(562);
//
//        SendRequestLuxexpress sendRequestLuxexpress = new SendRequestLuxexpress();
//        for (String from:fromList) {
//            Integer dest_id = destinationRepositoty.getDestIdByRequestValue(from);
//
//            if (!list.contains(dest_id) ) {
//
//                for (String to:toList) {
//
//                    String url = PATHLUX + from + "/" + to + "?Date=" + dateStr + "&Currency=CURRENCY.PLN";
//                    System.out.println(url);
//
//                    ClientResponse response = sendRequestLuxexpress.sendGetRequest(sendRequestLuxexpress.createWebResource(url), CONTENTTYPE);
//
//                    String responseStr = sendRequestLuxexpress.getResponseString(response);
//
//                    Boolean hasChanges = false;
//                    if (responseStr != null) {
//                        if (!((responseStr.contains("Prosimy wybrać jako odjazd"))
//                                || (responseStr.contains("Aby wyszukać podróż")))) {
//                            System.out.println("Route exist"+ from+":"+to);
//                            if (responseStr.contains("ico_transfer_route.gif")) {
//                                hasChanges = true;
//                            }
//                            if (responseStr.contains("amount")) {
//                                try {
//                                    Integer from_dest_id = destinationRepositoty.getDestIdByRequestValue(from);
//                                    Integer to_dest_id = destinationRepositoty.getDestIdByRequestValue(to);
//                                    Integer from_city_id = destinationRepositoty.getCityIdByRequestValue(from);
//                                    Integer to_city_id = destinationRepositoty.getCityIdByRequestValue(to);
//
//                                    System.out.println("Write to DB  "+from_dest_id + " " + to_dest_id + " " + from_city_id + " " + to_city_id + " " + companyId);
//
//                                    routeService.createRoute(from_dest_id, to_dest_id, from_city_id, to_city_id, companyId, true, hasChanges);
//                                } catch (Exception e) {
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
}
