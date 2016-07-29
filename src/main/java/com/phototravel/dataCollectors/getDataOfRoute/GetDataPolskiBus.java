package com.phototravel.dataCollectors.getDataOfRoute;

import com.phototravel.dataCollectors.outRequests.SendRequestPolskiBus;
import com.phototravel.dataCollectors.parser.PolskiBusParser;
import com.phototravel.entity.Destination;
import com.phototravel.entity.Price;
import com.phototravel.entity.Route;
import com.phototravel.repository.DestinationRepositoty;
import com.phototravel.repository.RouteRepository;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MultivaluedMap;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Olga_Govor on 7/20/2016.
 */
@Service
public class GetDataPolskiBus extends GetData {

    private static final String CONTENTTYPE = "application/json";
    private static final String PATHBASE = "https://booking.polskibus.com";
    private static final String PATHFORCOOKIE = "/Pricing/Selections?lang=PL";
    private static final String PATHGETPRICE = "/Pricing/GetPrice";
    // krakow/prague?Date=7-29-2016&Currency=CURRENCY.PLN"

    private static final String XPATHPRICE = "//div[@class='onb_resultRow']//p[@class='priceHilite']";
    private static final String XPATHDEPARTURE = "//div[@class='onb_resultRow']//div[@class='onb_col onb_two']//p[position() mod 2 = 1]/b";
    private static final String XPATHARRIVAL = "//div[@class='onb_resultRow']//div[@class='onb_col onb_two']//p[position() mod 2 = 0]/b";

    private static final String CURRENCY = "zl";
    private static final String COMPANYNAME = "PolskiBus";

//    public Route getData(Route route, String to, String from) throws Exception {
//
//
//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//        String date = formatter.format(route.getDateOfTrip());
//
//        SendRequestPolskiBus sendRequestPolskiBus = new SendRequestPolskiBus();
//
//        //send request to receive SessionId
//        WebResource webResource = sendRequestPolskiBus.createWebResource(PATHBASE + PATHFORCOOKIE);
//        ClientResponse response = sendRequestPolskiBus.sendGetRequest(webResource, CONTENTTYPE);
//        String cookieValue = sendRequestPolskiBus.getCookie(response);
//
//        //Map for PolskiBus request
//        MultivaluedMap map = new MultivaluedMapImpl();
//        map.add("PricingForm.Adults", "1");
//        map.add("PricingForm.ConcessionCod...", "");
//        map.add("PricingForm.DBType", "MY");
//        map.add("PricingForm.FromCity", from);
//        map.add("PricingForm.OutDate", date);
//        map.add("PricingForm.PromoCode", "");
//        map.add("PricingForm.RetDate", "");
//        map.add("PricingForm.ToCity", to);
//        map.add("PricingForm.hidSessionID", "");
//        map.add("Pricingform.hidLang", "PL");
//        map.add("Pricingform.hidPC", "");
//        map.add("__VIEWSTATE", "/wEPDwUJNzQxODA1MTQ4DxYCHhNWYWxpZGF0ZVJlcXVlc3RNb2RlAgFkZFPllo0+VPoB1LdmTlXTzZLAiP/sLBV1dT50WMo4RYHt");
//        map.add("__VIEWSTATEGENERATOR", "92D95504");
//
//        //send request for get temporary link with prices
//        WebResource webResource2 = sendRequestPolskiBus.createWebResource(PATHBASE + PATHGETPRICE);
//        Cookie cookie = new Cookie("ASP.NET_SessionId", cookieValue);
//        ClientResponse response2 = sendRequestPolskiBus.sendWidePostRequest(webResource2, cookie, "application/x-www-form-urlencoded", map);
//        String locationFromHeader = sendRequestPolskiBus.getLocationFromHeader(response2);
//
//        //send request using temporary link
//        WebResource webResource3 = sendRequestPolskiBus.createWebResource(PATHBASE + locationFromHeader );
//        ClientResponse response3 = sendRequestPolskiBus.sendGetRequestWithCookie(webResource3, CONTENTTYPE, cookie);
//        String responseStr = response3.getEntity(String.class);
//
//        PolskiBusParser parser = new PolskiBusParser();
//        route =  parser.parse(responseStr, route, COMPANYNAME, XPATHPRICE, XPATHDEPARTURE, XPATHARRIVAL, CURRENCY);
//        return route;
//    }

    @Autowired
    RouteRepository routeRepository;

    @Autowired
    DestinationRepositoty destinationRepositoty;

    public List<Price> getData(Integer routeId, Date date) throws Exception {


        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateStr = formatter.format(date);

        Iterable<Route> routes = routeRepository.findAll();
        com.phototravel.entity.Route route = routeRepository.findOne(new Long(routeId));

        Integer fromDestId = route.getFromDestinationId();
        Integer toDestId = route.getToDestinationId();

        Destination fromDestination = destinationRepositoty.findOne(new Long(fromDestId));
        Destination toDestination = destinationRepositoty.findOne(new Long(toDestId));
        String from = fromDestination.getRequestValue();
        String to = fromDestination.getRequestValue();

        SendRequestPolskiBus sendRequestPolskiBus = new SendRequestPolskiBus();

        //send request to receive SessionId
        WebResource webResource = sendRequestPolskiBus.createWebResource(PATHBASE + PATHFORCOOKIE);
        ClientResponse response = sendRequestPolskiBus.sendGetRequest(webResource, CONTENTTYPE);
        String cookieValue = sendRequestPolskiBus.getCookie(response);

        //Map for PolskiBus request
        MultivaluedMap map = new MultivaluedMapImpl();
        map.add("PricingForm.Adults", "1");
        map.add("PricingForm.ConcessionCod...", "");
        map.add("PricingForm.DBType", "MY");
        map.add("PricingForm.FromCity", from);
        map.add("PricingForm.OutDate", dateStr);
        map.add("PricingForm.PromoCode", "");
        map.add("PricingForm.RetDate", "");
        map.add("PricingForm.ToCity", to);
        map.add("PricingForm.hidSessionID", "");
        map.add("Pricingform.hidLang", "PL");
        map.add("Pricingform.hidPC", "");
        map.add("__VIEWSTATE", "/wEPDwUJNzQxODA1MTQ4DxYCHhNWYWxpZGF0ZVJlcXVlc3RNb2RlAgFkZFPllo0+VPoB1LdmTlXTzZLAiP/sLBV1dT50WMo4RYHt");
        map.add("__VIEWSTATEGENERATOR", "92D95504");

        //send request for get temporary link with prices
        WebResource webResource2 = sendRequestPolskiBus.createWebResource(PATHBASE + PATHGETPRICE);
        Cookie cookie = new Cookie("ASP.NET_SessionId", cookieValue);
        ClientResponse response2 = sendRequestPolskiBus.sendWidePostRequest(webResource2, cookie, "application/x-www-form-urlencoded", map);
        String locationFromHeader = sendRequestPolskiBus.getLocationFromHeader(response2);

        //send request using temporary link
        WebResource webResource3 = sendRequestPolskiBus.createWebResource(PATHBASE + locationFromHeader );
        ClientResponse response3 = sendRequestPolskiBus.sendGetRequestWithCookie(webResource3, CONTENTTYPE, cookie);
        String responseStr = response3.getEntity(String.class);

        PolskiBusParser parser = new PolskiBusParser();
        List<Price> listOfPrices;
        listOfPrices =  parser.parse(responseStr, routeId, COMPANYNAME, XPATHPRICE, XPATHDEPARTURE, XPATHARRIVAL, CURRENCY);
        return listOfPrices;
    }
}
