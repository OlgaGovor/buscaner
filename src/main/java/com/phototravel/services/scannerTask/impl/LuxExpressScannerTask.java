package com.phototravel.services.scannerTask.impl;


import com.phototravel.entity.Price;
import com.phototravel.services.parser.LuxexpressParser;
import com.phototravel.services.scannerTask.AbstractBusScannerTask;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("Luxexpress")
@Scope("prototype")
public class LuxExpressScannerTask extends AbstractBusScannerTask {
    private Logger logger = null;

    @Autowired
    LuxexpressParser parser;


    public LuxExpressScannerTask() {
    }

    @Override
    public void run() {

        logger = LoggerFactory.getLogger(this.getClass() + " " + Thread.currentThread().getName());

        logger.info("scan for " + route + date);

        WebResource webResource = createWebResource(buildRequestUrl());
        ClientResponse response = sendGetRequest(webResource, CONTENT_TYPE);
        String responseStr = getResponseString(response);

        List<Price> listOfPrices = new ArrayList<>();
        try {
            listOfPrices = parser.parse(responseStr, route.getRouteId(), date, config);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("result price size=" + listOfPrices.size());
        savePrices(listOfPrices);

    }

    @Override
    protected String buildRequestUrl() {
        String dateStr = date.format(formatter);
        logger.info("url = " + config.getParam("BASE_URL") + getFromRequestValue() + "/" + getToRequestValue() +
                "?Date=" + dateStr + "&Currency=" + config.getParam("CURRENCY_REQUEST_VALUE"));
        return config.getParam("BASE_URL") + getFromRequestValue() + "/" + getToRequestValue() +
                "?Date=" + dateStr + "&Currency=" + config.getParam("CURRENCY_REQUEST_VALUE");
    }
}
