package com.phototravel.services.companiesConfig.impl;


import com.phototravel.services.DestinationService;
import com.phototravel.services.companiesConfig.AbstractConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component("LuxexpressConfig")
public class LuxExpressConfig extends AbstractConfig {

   /* public static final String XPATH_PRICE = "//div[contains (@class, 'regular-body')]//span[@class = 'amount']";
    public static final String XPATH_DEPARTURE = "//div[contains(@class,'row times')]/div/span[1]";
    public static final String XPATH_ARRIVAL = "//div[contains(@class,'row times')]/div/span[2]";
    public static final String XPATH_DURATION = "//div[contains(@class, 'duration')]";

    public static final String BASE_URL = "https://ticket.luxexpress.eu/pl/wyjazdy-harmonogram/";

    public static final String CURRENCY = "EUR";
    private static final String CURRENCY_REQUEST_VALUE = "CURRENCY.PLN";*/

    @Autowired
    private DestinationService destinationService;

    public LuxExpressConfig() {
        configParams.put("BASE_URL", "https://ticket.luxexpress.eu/pl/wyjazdy-harmonogram/");

        configParams.put("XPATH_PRICE", "//div[contains (@class, 'regular-body')]//span[@class = 'amount']");
        configParams.put("XPATH_DEPARTURE", "//div[contains(@class,'row times')]/div/span[1]");
        configParams.put("XPATH_ARRIVAL", "//div[contains(@class,'row times')]/div/span[2]");
        configParams.put("XPATH_DURATION", "//div[contains(@class, 'duration')]");

        configParams.put("CURRENCY", "PLN");
        configParams.put("CURRENCY_REQUEST_VALUE", "CURRENCY.PLN");
    }


    public String getRedirectUrl(String fromRequestValue, String toRequestValue, String date) {

        DateTimeFormatter formatterFromUI = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateOfTrip = LocalDate.parse(date, formatterFromUI);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        String dateStr = dateOfTrip.format(formatter);

        String url = getParam("BASE_URL") + fromRequestValue + "/" + toRequestValue + "?Date=" + dateStr + "&Currency=CURRENCY." + getParam("CURRENCY");
        return url;
    }


}
