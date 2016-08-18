package com.phototravel.services.impl;

import com.phototravel.RequestSender;
import com.phototravel.outerRequests.SendRequestPolskiBus;
import com.phototravel.services.parser.PolskiBusParser;
import com.phototravel.entity.Price;
import com.phototravel.modelOfFetcher.FetcherType;
import com.phototravel.services.Fetcher;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MultivaluedMap;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olga_Govor on 8/16/2016.
 */
public class PolskiBusFetcher implements Fetcher {
    private static final FetcherType type = FetcherType.POLSKI_BUS;

    private static final String CONTENTTYPE = "application/json";
    private static final String PATHBASE = "https://booking.polskibus.com";
    private static final String PATHFORCOOKIE = "/Pricing/Selections?lang=PL";
    private static final String PATHGETPRICE = "/Pricing/GetPrice";

    private static final String XPATHPRICE = "//div[@class='onb_resultRow']//p[@class='priceHilite']";
    private static final String XPATHDEPARTURE = "//div[@class='onb_resultRow']//div[@class='onb_col onb_two']//p[position() mod 2 = 1]/b";
    private static final String XPATHARRIVAL = "//div[@class='onb_resultRow']//div[@class='onb_col onb_two']//p[position() mod 2 = 0]/b";

    private static final String CURRENCY = "zl";

    @Autowired
    RequestSender requestSender;

    @Override
    public List<Price> fetch(String fromRequestValue, String toRequestValue, LocalDate date, int routeId) {


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateStr = date.format(formatter);
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
        map.add("PricingForm.FromCity", fromRequestValue);
        map.add("PricingForm.OutDate", dateStr);
        map.add("PricingForm.PromoCode", "");
        map.add("PricingForm.RetDate", "");
        map.add("PricingForm.ToCity", toRequestValue);
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
        List<Price> listOfPrices = new ArrayList<>();
        try {
            listOfPrices =  parser.parse(responseStr, routeId, date, XPATHPRICE, XPATHDEPARTURE, XPATHARRIVAL, CURRENCY);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return listOfPrices;
        }
    }

    @Override
    public FetcherType getType() {
        return type;
    }
}
