package com.phototravel.services.companiesConfig.impl;


import com.phototravel.services.DestinationService;
import com.phototravel.services.companiesConfig.AbstractConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("PolskiBusConfig")
public class PolskiBusConfig extends AbstractConfig {

   /* private static final String CONTENT_TYPE = "application/json";
    private static final String BASE_URL = "https://booking.polskibus.com";
    private static final String PATH_FOR_COOKIE = "/Pricing/Selections?lang=PL";
    private static final String PATH_GET_PRICE = "/Pricing/GetPrice";

    private static final String XPATH_PRICE = "//div[@class='onb_resultRow']//p[@class='priceHilite']";
    private static final String XPATH_DEPARTURE = "//div[@class='onb_resultRow']//div[@class='onb_col onb_two']//p[position() mod 2 = 1]/b";
    private static final String XPATH_ARRIVAL = "//div[@class='onb_resultRow']//div[@class='onb_col onb_two']//p[position() mod 2 = 0]/b";
    private static final String XPATH_DURATION = "//div[@class='onb_resultRow']//div[contains(@class,'onb_three')]//strong";

    private static final String CURRENCY = "PLN";
    private static final String CURRENCY_REQUEST_VALUE = "CURRENCY.PLN";*/

    @Autowired
    private DestinationService destinationService;

    public PolskiBusConfig() {
        configParams.put("BASE_URL", "https://booking.polskibus.com");
        configParams.put("CONTENT_TYPE", "application/json");
        configParams.put("PATH_FOR_COOKIE", "/Pricing/Selections?lang=PL");
        configParams.put("PATH_GET_PRICE", "/Pricing/GetPrice");

        configParams.put("XPATH_PRICE", "//div[@class='onb_resultRow']//p[@class='priceHilite']");
        configParams.put("XPATH_DEPARTURE", "//div[@class='onb_resultRow']//div[@class='onb_col onb_two']//p[position() mod 2 = 1]/b");
        configParams.put("XPATH_ARRIVAL", "//div[@class='onb_resultRow']//div[@class='onb_col onb_two']//p[position() mod 2 = 0]/b");
        configParams.put("XPATH_DURATION", "//div[@class='onb_resultRow']//div[contains(@class,'onb_three')]//strong");

        configParams.put("CURRENCY", "PLN");
        configParams.put("CURRENCY_REQUEST_VALUE", "CURRENCY.PLN");

    }


    public String getRedirectUrl(String fromRequestValue, String toRequestValue, String date) {
        String url = getParam("BASE_URL") + getParam("PATH_FOR_COOKIE");
        return url;
    }
}
