package com.phototravel.services.scannerTask.impl;

import com.phototravel.entity.Price;
import com.phototravel.services.parser.PolskiBusParser;
import com.phototravel.services.scannerTask.AbstractBusScannerTask;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MultivaluedMap;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component("PolskiBus")
@Scope("prototype")
public class PolskiBusScannerTask extends AbstractBusScannerTask {

    private Logger logger = null;

    @Autowired
    PolskiBusParser parser;

    @Override
    public void run() {
        logger = LoggerFactory.getLogger(this.getClass() + " " + Thread.currentThread().getName());

        logger.info("scan for " + route + date);

        //send request to receive SessionId
        WebResource webResource = createWebResource(config.getParam("BASE_URL") + config.getParam("PATH_FOR_COOKIE"));
        ClientResponse response = sendGetRequest(webResource, config.getParam("CONTENT_TYPE"));
        String cookieValue = getCookie(response);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");


        //Map for PolskiBus request
        MultivaluedMap map = new MultivaluedMapImpl();
        map.add("PricingForm.Adults", "1");
        map.add("PricingForm.ConcessionCod...", "");
        map.add("PricingForm.DBType", "MY");
        map.add("PricingForm.FromCity", getFromRequestValue());
        map.add("PricingForm.OutDate", date.format(formatter));
        map.add("PricingForm.PromoCode", "");
        map.add("PricingForm.RetDate", "");
        map.add("PricingForm.ToCity", getToRequestValue());
        map.add("PricingForm.hidSessionID", "");
        map.add("Pricingform.hidLang", "PL");
        map.add("Pricingform.hidPC", "");
        map.add("__VIEWSTATE", "/wEPDwUJNzQxODA1MTQ4DxYCHhNWYWxpZGF0ZVJlcXVlc3RNb2RlAgFkZFPllo0+VPoB1LdmTlXTzZLAiP/sLBV1dT50WMo4RYHt");
        map.add("__VIEWSTATEGENERATOR", "92D95504");

        //send request for get temporary link with prices
        WebResource webResource2 = createWebResource(config.getParam("BASE_URL") + config.getParam("PATH_GET_PRICE"));
        Cookie cookie = new Cookie("ASP.NET_SessionId", cookieValue);
        ClientResponse response2 = sendWidePostRequest(webResource2, cookie, "application/x-www-form-urlencoded", map);
        String locationFromHeader = getLocationFromHeader(response2);

        //send request using temporary link
        WebResource webResource3 = createWebResource(config.getParam("BASE_URL") + locationFromHeader);
        ClientResponse response3 = sendGetRequestWithCookie(webResource3, config.getParam("CONTENT_TYPE"), cookie);
        String responseStr = response3.getEntity(String.class);

        PolskiBusParser parser = new PolskiBusParser();
        List<Price> listOfPrices = new ArrayList<>();
        try {
            listOfPrices = parser.parse(responseStr, route.getRouteId(), date, config);
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.info("result price size=" + listOfPrices.size());
        savePrices(listOfPrices);
    }


    public String getCookie(ClientResponse response) {
        String headerStr = "";
        if (response.getClientResponseStatus() == ClientResponse.Status.OK) {
            MultivaluedMap<String, String> headers = response.getHeaders();
            headerStr = headers.get("Set-Cookie").toString();
        }

        String cookieValue = headerStr.substring(19, 43);
        return cookieValue;
    }
}
