package com.phototravel.dataCollectors.getDataOfRoute;


import com.phototravel.dataCollectors.Route;
import com.phototravel.dataCollectors.outRequests.SendRequestLuxexpress;
import com.phototravel.dataCollectors.parser.LuxexpressParser;
import com.sun.jersey.api.client.ClientResponse;

import java.text.SimpleDateFormat;

/**
 * Created by Olga_Govor on 7/20/2016.
 */
public class GetDataLuxexpress {

    private static final String CONTENTTYPE = "application/json";
    private static final String PATHLUX = "http://ticket.luxexpress.eu/pl/wyjazdy-harmonogram/";

    private static final String XPATHPRICE = "//div[contains (@class, 'regular-fullPrice')]//span[@class = 'amount']";
    private static final String XPATHDEPARTURE = "//div[contains(@class,'row times')]/div/span[1]";
    private static final String XPATHARRIVAL = "//div[contains(@class,'row times')]/div/span[2]";

    private static final String CURRENCY = "zl";
    private static final String COMPANYNAME = "Luxexpress";

    public Route getData(Route route, String to, String from) throws Exception {

        SendRequestLuxexpress sendRequestLuxexpress = new SendRequestLuxexpress();

        //
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        String date = formatter.format(route.getDateOfTrip());
        //get 'to' and 'from' from Route and update it for company
        String url = PATHLUX + from + "/" + to + "?Date=" + date +"&Currency=CURRENCY.PLN";

        //send request
        ClientResponse response = sendRequestLuxexpress.sendGetRequest(sendRequestLuxexpress.createWebResource(url), CONTENTTYPE);
        String responseStr = sendRequestLuxexpress.getResponseString(response);

        //parse result
        LuxexpressParser parser = new LuxexpressParser();
        route =  parser.parse(responseStr, route, COMPANYNAME, XPATHPRICE, XPATHDEPARTURE, XPATHARRIVAL, CURRENCY);

        return route;
    }
}
