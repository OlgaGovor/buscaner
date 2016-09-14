package com.phototravel.services.impl;

import com.phototravel.entity.Destination;
import com.phototravel.entity.Price;
import com.phototravel.entity.Route;
import com.phototravel.services.DestinationService;
import com.phototravel.services.dbWriter.DBWriterService;
import com.phototravel.services.impl.luxExpress.Task;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


public abstract class BusScanTask implements Task {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DestinationService destinationService;
    @Autowired
    DBWriterService dbWriterService;


    protected Route route;
    protected LocalDate date;

    protected DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

    protected static final String CONTENT_TYPE = "application/json";

    public BusScanTask() {
    }

    public BusScanTask(Route route, LocalDate date) {
        this.route = route;
        this.date = date;

    }

    protected WebResource createWebResource(String url) {
        Client client = Client.create();
        client.setFollowRedirects(false);
        WebResource webResource = client.resource(url);
        return webResource;
    }

    public ClientResponse sendGetRequest(WebResource webResource, String dataType) {
        ClientResponse response = webResource
                .type(dataType)
                .accept(dataType)
                .get(ClientResponse.class);

        return response;
    }

    public String getResponseString(ClientResponse response) {
        if (response.getClientResponseStatus() == ClientResponse.Status.OK) {
            String responseStr = response.getEntity(String.class);
            return responseStr;
        }
        return null;
    }

    protected String getFromRequestValue() {
        Integer fromDestId = route.getFromDestinationId();
        Destination fromDestination = destinationService.findOne(fromDestId);
        return fromDestination.getRequestValue();
    }

    protected String getToRequestValue() {
        Integer toDestId = route.getToDestinationId();
        Destination toDestination = destinationService.findOne(toDestId);
        return toDestination.getRequestValue();
    }

    protected String buildRequestUrl() {
        return "";
    }

    protected void savePrices(List<Price> prices) {
        for (Price price : prices) {
            try {
                dbWriterService.addToQueue(price);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
