package com.buscanner.outRest;

import com.buscanner.Route;
import com.buscanner.RouteDetails;
import com.buscanner.parser.LuxexpressParser;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.text.SimpleDateFormat;

/**
 * Created by Olga_Govor on 6/29/2016.
 */
public class SendRest {

    private static final String PATHLUX = "http://ticket.luxexpress.eu/pl/wyjazdy-harmonogram/";
    private static final String PATHPOLSKIBUS = "https://booking.polskibus.com/Pricing/Selections?lang=PL";
    // krakow/prague?Date=7-29-2016&Currency=CURRENCY.PLN"
    private static final String CONTENTTYPE = "application/json";

    public void getLuxexpress(Route route) throws XPathExpressionException, ParserConfigurationException {

        LuxexpressParser parser = new LuxexpressParser();

        Client client = Client.create();
        //form Path from parametr route and get data from DB
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        String date = formatter.format(route.getDateOfTrip());
        WebResource webResource = client.resource(PATHLUX + route.getFrom() + "/" + route.getTo() + "?Date=" + date +"&Currency=CURRENCY.PLN");

        ClientResponse response = webResource
                .type(CONTENTTYPE)
                .accept(CONTENTTYPE)
                .get(ClientResponse.class);

        if (response.getClientResponseStatus() == ClientResponse.Status.OK)
        {
            String responseStr = response.getEntity(String.class);
            route =  parser.parseLuxExpress(responseStr, route);

            printRouteWithDetails(route);

        }
    }



    public void getPolskibus(Route route) throws XPathExpressionException, ParserConfigurationException {

//        LuxexpressParser parser = new LuxexpressParser();

        Client client = Client.create();
        //form Path from parametr route and get data from DB
//        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
//        String date = formatter.format(route.getDateOfTrip());
        WebResource webResource = client.resource(PATHPOLSKIBUS);

        ClientResponse response = webResource.get(ClientResponse.class);

        String headerStr = "";

        if (response.getClientResponseStatus() == ClientResponse.Status.OK)
        {
            MultivaluedMap<String, String> headers = response.getHeaders();
            headerStr = headers.get("Set-Cookie").toString();
        }

        String cookieValue = headerStr.substring(19,43);
        System.out.println(cookieValue);

        WebResource webResource2 = client.resource("https://booking.polskibus.com/Pricing/GetPrice");

        MultivaluedMap map = new MultivaluedMapImpl();
        map.add("PricingForm.Adults", "1");
        map.add("PricingForm.ConcessionCod...", "");
        map.add("PricingForm.DBType", "MY");
        map.add("PricingForm.FromCity", "15");
        map.add("PricingForm.OutDate", "20/07/2016");
        map.add("PricingForm.PromoCode", "");
        map.add("PricingForm.RetDate", "");
        map.add("PricingForm.ToCity", "45");
        map.add("PricingForm.hidSessionID", "");
        map.add("Pricingform.hidLang", "PL");
        map.add("Pricingform.hidPC", "PL");
        map.add("__VIEWSTATE", "/wEPDwUJNzQxODA1MTQ4DxYCHhNWYWxpZGF0ZVJlcXVlc3RNb2RlAgFkZJFAmJpuigDnmbIfvPCH6ILQaD10uPlbFW/7kGbB/bNO");
        map.add("__VIEWSTATEGENERATOR", "92D9550");


        Cookie cookie = new Cookie("ASP.NET_SessionId", cookieValue);

        ClientResponse response2 = webResource2
                .cookie(cookie)
                .type("application/x-www-form-urlencoded")
                .post(ClientResponse.class, map);
//            route =  parser.parseLuxExpress(responseStr, route);

        String ent = response2.getEntity(String.class);


//        MultivaluedMap<String, String> headers = response2.getHeaders();
//        headerStr = headers.get("Location").toString();
        System.out.println(ent);

//        printRouteWithDetails(route);

    }



    public void printRouteWithDetails(Route r){
        System.out.println("From:" + r.getFrom() + " To:" + r.getTo() + " Date:" + r.getDateOfTrip());
        for (RouteDetails node: r.getDetails()) {
            System.out.println("Price:"+ node.getPrice()+ " Departure:" + node.getTimeDeparture() + " Arrival:" + node.getTimeArrival() + " Company:" + node.getCompany() + "");
        }
    }
}
