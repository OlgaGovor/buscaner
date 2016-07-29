package com.phototravel.dataCollectors.getDataOfRoute;


import com.phototravel.dataCollectors.outRequests.SendRequestLuxexpress;
import com.phototravel.dataCollectors.parser.LuxexpressParser;
import com.phototravel.entity.Destination;
import com.phototravel.entity.Price;
import com.phototravel.repository.DestinationRepositoty;
import com.phototravel.repository.RouteRepository;
import com.sun.jersey.api.client.ClientResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Olga_Govor on 7/20/2016.
 */
public class GetDataLuxexpress extends GetData {

    private static final String CONTENTTYPE = "application/json";
    private static final String PATHLUX = "http://ticket.luxexpress.eu/pl/wyjazdy-harmonogram/";

    private static final String XPATHPRICE = "//div[contains (@class, 'regular-fullPrice')]//span[@class = 'amount']";
    private static final String XPATHDEPARTURE = "//div[contains(@class,'row times')]/div/span[1]";
    private static final String XPATHARRIVAL = "//div[contains(@class,'row times')]/div/span[2]";

    private static final String CURRENCY = "zl";
    private static final String COMPANYNAME = "Luxexpress";

    @Autowired
    RouteRepository routeRepository;

    @Autowired
    DestinationRepositoty destinationRepositoty;

//    public Route getData(Route route, String to, String from) throws Exception {
//
//        SendRequestLuxexpress sendRequestLuxexpress = new SendRequestLuxexpress();
//
//        //
//        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
//        String date = formatter.format(route.getDateOfTrip());
//        //get 'to' and 'from' from Route and update it for company
//        String url = PATHLUX + from + "/" + to + "?Date=" + date +"&Currency=CURRENCY.PLN";
//
//        //send request
//        ClientResponse response = sendRequestLuxexpress.sendGetRequest(sendRequestLuxexpress.createWebResource(url), CONTENTTYPE);
//        String responseStr = sendRequestLuxexpress.getResponseString(response);
//
//        //parse result
//        LuxexpressParser parser = new LuxexpressParser();
////        about modification
////        route =  parser.parse(responseStr, route, COMPANYNAME, XPATHPRICE, XPATHDEPARTURE, XPATHARRIVAL, CURRENCY);
//
//        return route;
//    }

    public List<Price> getData(Integer routeId, Date date) throws Exception {

        SendRequestLuxexpress sendRequestLuxexpress = new SendRequestLuxexpress();

        //
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        String dateStr = formatter.format(date);

        //get 'to' and 'from' from Route and update it for company
        com.phototravel.entity.Route route = routeRepository.getRouteByRouteId(routeId);

        Integer fromDestId = route.getFromDestinationId();
        Integer toDestId = route.getToDestinationId();

        Destination fromDestination = destinationRepositoty.findOne(fromDestId);
        Destination toDestination = destinationRepositoty.findOne(toDestId);
        String from = fromDestination.getRequestValue();
        String to = toDestination.getRequestValue();

        String url = PATHLUX + from + "/" + to + "?Date=" + dateStr +"&Currency=CURRENCY.PLN";

        //send request
        ClientResponse response = sendRequestLuxexpress.sendGetRequest(sendRequestLuxexpress.createWebResource(url), CONTENTTYPE);
        String responseStr = sendRequestLuxexpress.getResponseString(response);

        //parse result
        LuxexpressParser parser = new LuxexpressParser();
//        about modification
        List<Price> listOfPrices;
        listOfPrices =  parser.parse(responseStr, routeId, date, XPATHPRICE, XPATHDEPARTURE, XPATHARRIVAL, CURRENCY);
        return listOfPrices;

    }
}
