package com.phototravel.iteration.service.impl;

import com.phototravel.dataCollectors.outRequests.SendRequestLuxexpress;
import com.phototravel.dataCollectors.parser.LuxexpressParser;
import com.phototravel.entity.Price;
import com.phototravel.iteration.model.FetcherType;
import com.phototravel.iteration.service.Fetcher;
import com.sun.jersey.api.client.ClientResponse;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olga_Govor on 8/16/2016.
 */
public class LuxexpressFetcher implements Fetcher{
    private static final FetcherType type = FetcherType.LUX_EXPRESS;

    private static final String CONTENTTYPE = "application/json";
    private static final String PATHLUX = "https://ticket.luxexpress.eu/pl/wyjazdy-harmonogram/";

    private static final String XPATHPRICE = "//div[contains (@class, 'regular-body')]//span[@class = 'amount']";
    private static final String XPATHDEPARTURE = "//div[contains(@class,'row times')]/div/span[1]";
    private static final String XPATHARRIVAL = "//div[contains(@class,'row times')]/div/span[2]";

    private static final String CURRENCY = "zl";

    @Override
    public List<Price> fetch(String fromRequestValue, String toRequestValue, LocalDate date, int routeId) {
        SendRequestLuxexpress sendRequestLuxexpress = new SendRequestLuxexpress();

        //
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        String dateStr = date.format(formatter);

        String url = PATHLUX + fromRequestValue + "/" + toRequestValue + "?Date=" + dateStr +"&Currency=CURRENCY.PLN";

        //send request
        ClientResponse response = sendRequestLuxexpress.sendGetRequest(sendRequestLuxexpress.createWebResource(url), CONTENTTYPE);
        String responseStr = sendRequestLuxexpress.getResponseString(response);

        //parse result
        LuxexpressParser parser = new LuxexpressParser();
//        about modification
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
