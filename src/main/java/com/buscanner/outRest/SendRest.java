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
        client.setFollowRedirects(false);
        //form Path from parametr route and get data from DB
//        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
//        String date = formatter.format(route.getDateOfTrip());
        WebResource webResource = client.resource("https://booking.polskibus.com/Pricing/Selections?lang=PL");

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
        map.add("Pricingform.hidPC", "");
        map.add("__VIEWSTATE", "/wEPDwUJNzQxODA1MTQ4DxYCHhNWYWxpZGF0ZVJlcXVlc3RNb2RlAgFkZFPllo0+VPoB1LdmTlXTzZLAiP/sLBV1dT50WMo4RYHt");
        map.add("__VIEWSTATEGENERATOR", "92D95504");

//        Cookie cookie = new Cookie("ASP.NET_SessionId", "4p3sxktxrzjb32cxyxd0czvb");
        Cookie cookie = new Cookie("ASP.NET_SessionId", cookieValue);

        webResource2.setProperty("Host", "booking.polskibus.com");
        webResource2.setProperty("Connection", "keep-alive");
        webResource2.setProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        webResource2.setProperty("Accept-Encoding", "gzip, deflate, br");
        webResource2.setProperty("Accept-Language", "ru-RU,ru;q=0.8,en-US;q=0.5,en;q=0.3");
        webResource2.setProperty("Referer", "https://booking.polskibus.com/Pricing/Selections?lang=PL");
        webResource2.setProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:47.0) Gecko/20100101 Firefox/47.0");

        ClientResponse response2 = webResource2
                .cookie(cookie)
                .type("application/x-www-form-urlencoded")
                .post(ClientResponse.class, map);


        MultivaluedMap<String, String> headers = response2.getHeaders();
        headerStr = headers.get("Location").toString();
        headerStr = headerStr.substring(1, headerStr.length()-1);
        System.out.println(headerStr);

        WebResource webResource3 = client.resource("https://booking.polskibus.com" + headerStr);
//        WebResource webResource3 = client.resource("https://booking.polskibus.com/Pricing/ShowResults?SID=4p3sxktx13163950413yxd0czvb&CS=9688330");

        webResource3.setProperty("Host", "booking.polskibus.com");
        webResource3.setProperty("Connection", "keep-alive");
        webResource3.setProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        webResource3.setProperty("Accept-Encoding", "gzip, deflate, br");
        webResource3.setProperty("Accept-Language", "ru-RU,ru;q=0.8,en-US;q=0.5,en;q=0.3");
        webResource3.setProperty("Referer", "https://booking.polskibus.com/Pricing/Selections?lang=PL");
        webResource3.setProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:47.0) Gecko/20100101 Firefox/47.0");

//        Cookie cookie3 = new Cookie("ASP.NET_SessionId", "4p3sxktxrzjb32cxyxd0czvb");
        Cookie cookie3 = cookie;

        ClientResponse response3 = webResource3
                .cookie(cookie3)
                .get(ClientResponse.class);

        String ent = response3.getEntity(String.class);
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
