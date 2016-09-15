package com.phototravel.services.scannerTask;

import com.phototravel.entity.Destination;
import com.phototravel.entity.Price;
import com.phototravel.entity.Route;
import com.phototravel.services.DestinationService;
import com.phototravel.services.companiesConfig.Config;
import com.phototravel.services.companiesConfig.ConfigFactory;
import com.phototravel.services.dbWriter.DBWriterService;
import com.phototravel.services.dbWriter.QueuedItemContainer;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MultivaluedMap;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


public abstract class AbstractBusScannerTask implements BusScannerTask {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected DestinationService destinationService;
    @Autowired
    protected DBWriterService dbWriterService;

    @Autowired
    protected ConfigFactory configFactory;

    Config config;

    protected Route route;
    protected LocalDate date;
    protected TaskCallback callback;
    protected String companyName;

    protected DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

    protected static final String CONTENT_TYPE = "application/json";

    public AbstractBusScannerTask() {
    }


    void initConfig() {
        config = configFactory.getConfig(companyName + "Config");
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

    public ClientResponse sendWidePostRequest(WebResource webResource, Cookie cookie, String dataType, MultivaluedMap map) {
        ClientResponse response = webResource
                .cookie(cookie)
                .type(dataType)
                .post(ClientResponse.class, map);

        return response;
    }

    public String getLocationFromHeader(ClientResponse response) {
        MultivaluedMap<String, String> headers = response.getHeaders();

        String headerStr = headers.get("Location").toString();
        headerStr = headerStr.substring(1, headerStr.length() - 1);
        return headerStr;
    }

    public ClientResponse sendGetRequestWithCookie(WebResource webResource, String dataType, Cookie cookie) {
        ClientResponse response = webResource
                .type(dataType)
                .cookie(cookie)
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
        int rowNumber = 1;
        for (Price price : prices) {
            try {
                QueuedItemContainer<Price> priceContainer = new QueuedItemContainer<Price>();
                priceContainer.setItem(price);
                if (rowNumber == prices.size()) {
                    priceContainer.setCallback(callback);
                }
                rowNumber++;
                dbWriterService.addToQueue(priceContainer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (prices == null || prices.isEmpty()) {
            callback();
        }
       /* if(prices == null || prices.isEmpty())
        {
            Date date1 = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Price nullPrice = new Price.NullPrice(route.getRouteId(), date1);
            try {
                dbWriterService.addToQueue(nullPrice);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/

    }

    protected void callback() {
        if (callback != null) {
            callback.taskDone();
        }
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setCallback(TaskCallback callback) {
        this.callback = callback;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
        initConfig();
    }
}
