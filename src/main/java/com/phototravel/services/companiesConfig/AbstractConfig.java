package com.phototravel.services.companiesConfig;

import com.phototravel.entity.Destination;
import com.phototravel.entity.Route;
import com.phototravel.services.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;


public abstract class AbstractConfig implements Config {

    protected Map<String, String> configParams = new HashMap<>();

    @Autowired
    private DestinationService destinationService;

    public String getParam(String paramName) {
        return configParams.get(paramName);
    }

    public String getLink(Route route, String date) {

        Destination fromDestination = destinationService.findOne(route.getFromDestinationId());
        Destination toDestination = destinationService.findOne(route.getToDestinationId());
        String fromRequestValue = fromDestination.getRequestValue();
        String toRequestValue = toDestination.getRequestValue();


        String url = getRedirectUrl(fromRequestValue, toRequestValue, date);
        return url;
    }

    protected abstract String getRedirectUrl(String fromRequestValue, String toRequestValue, String date);
}
