package com.phototravel.services.impl.luxExpress;


import com.phototravel.entity.Price;
import com.phototravel.entity.Route;
import com.phototravel.services.DestinationService;
import com.phototravel.services.dbWriter.DBWriterService;
import com.phototravel.services.impl.BusScanTask;
import com.phototravel.services.parser.LuxexpressParser;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component("Luxexpress")
public class LuxExpressTask extends BusScanTask {
    private final Logger logger = LoggerFactory.getLogger(this.getClass() + " " + Thread.currentThread().getName());


    private static final String BASE_URL = "https://ticket.luxexpress.eu/pl/wyjazdy-harmonogram/";
    private static final String CURRENCY_REQUEST_VALUE = "CURRENCY.PLN";


    @Autowired
    LuxexpressParser parser = new LuxexpressParser();

    public LuxExpressTask() {

    }

    public LuxExpressTask(Route route, LocalDate date) {
        super(route, date);
    }


    @Override
    public void run() {


        logger.info("scan for " + route + date);

        WebResource webResource = createWebResource(buildRequestUrl());
        ClientResponse response = sendGetRequest(webResource, CONTENT_TYPE);
        String responseStr = getResponseString(response);

        List<Price> listOfPrices = new ArrayList<>();
        try {
            listOfPrices = parser.parse(responseStr, route.getRouteId(), date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("result size=" + listOfPrices.size());
        savePrices(listOfPrices);


    }

    @Override
    protected String buildRequestUrl() {
        String dateStr = date.format(formatter);
        logger.info("url = " + BASE_URL + getFromRequestValue() + "/" + getToRequestValue() + "?Date=" + dateStr + "&Currency=" + CURRENCY_REQUEST_VALUE);
        return BASE_URL + getFromRequestValue() + "/" + getToRequestValue() + "?Date=" + dateStr + "&Currency=" + CURRENCY_REQUEST_VALUE;
    }
}
