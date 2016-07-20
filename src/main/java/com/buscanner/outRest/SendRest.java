package com.buscanner.outRest;

import com.buscanner.Route;
import com.buscanner.parser.LuxexpressParser;
import com.buscanner.parser.MegabusParser;
import com.buscanner.parser.PolskiBusParser;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MultivaluedMap;
import java.text.SimpleDateFormat;

/**
 * Created by Olga_Govor on 6/29/2016.
 */
public class SendRest {

    private static final String PATHLUX = "http://ticket.luxexpress.eu/pl/wyjazdy-harmonogram/";
    private static final String PATHMEGABUS = "http://deeu.megabus.com/JourneyResults.aspx?";

    private static final String PATHPOLSKIBUS = "https://booking.polskibus.com/Pricing/Selections?lang=PL";
    // krakow/prague?Date=7-29-2016&Currency=CURRENCY.PLN"
    private static final String CONTENTTYPE = "application/json";

    public ClientResponse sendGetRequest(WebResource webResource, String dataType)
    {
        ClientResponse response = webResource
                .type(dataType)
                .accept(dataType)
                .get(ClientResponse.class);

        return response;
    }

    public ClientResponse sendGetRequestWithCookie(WebResource webResource, String dataType, Cookie cookie)
    {
        ClientResponse response = webResource
                .type(dataType)
                .cookie(cookie)
                .get(ClientResponse.class);

        return response;
    }

    public WebResource createWebResource (String url)
    {
        Client client = Client.create();
        client.setFollowRedirects(false);
        WebResource webResource = client.resource(url);
        return webResource;
    }

    public String getCookieForPolskiBus (ClientResponse response)
    {
        String headerStr = "";
        if (response.getClientResponseStatus() == ClientResponse.Status.OK)
        {
            MultivaluedMap<String, String> headers = response.getHeaders();
            headerStr = headers.get("Set-Cookie").toString();
        }

        String cookieValue = headerStr.substring(19,43);
        return cookieValue;
    }

    public String getLocationFromHeader (ClientResponse response)
    {
        MultivaluedMap<String, String> headers = response.getHeaders();

        String headerStr = headers.get("Location").toString();
        headerStr = headerStr.substring(1, headerStr.length()-1);
        return  headerStr;
    }

    public String getResponseString(ClientResponse response)
    {
        if (response.getClientResponseStatus() == ClientResponse.Status.OK) {
            String responseStr = response.getEntity(String.class);
            return  responseStr;
        }
        return null;
    }

    public ClientResponse sendWidePostRequest(WebResource webResource, Cookie cookie, String dataType, MultivaluedMap map)
    {
        ClientResponse response = webResource
                .cookie(cookie)
                .type(dataType)
                .post(ClientResponse.class, map);

        return response;
    }

    public Route getLuxexpress(Route route, String to, String from) throws Exception {

        //XPathes for parser
        String xPathPrice = "//div[contains (@class, 'regular-fullPrice')]//span[@class = 'amount']";
        String xPathDeparture = "//div[contains(@class,'row times')]/div/span[1]";
        String xPathArrival = "//div[contains(@class,'row times')]/div/span[2]";

        //
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        String date = formatter.format(route.getDateOfTrip());
        //get 'to' and 'from' from Route and update it for company
        String url = PATHLUX + from + "/" + to + "?Date=" + date +"&Currency=CURRENCY.PLN";

        //send request
        ClientResponse response = sendGetRequest(createWebResource(url), CONTENTTYPE);
        String responseStr = getResponseString(response);

        //parse result
        LuxexpressParser parser = new LuxexpressParser();
        route =  parser.parse(responseStr, route, "LuxExpress", xPathPrice, xPathDeparture, xPathArrival, "zl");

        return route;
    }

    public void getMegabus(Route route) throws Exception {

        Client client = Client.create();
        //form Path from parametr route and get data from DB
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String date = formatter.format(route.getDateOfTrip());
        //Parameters
        String from = "originCode="+"197";
        String to = "&destinationCode="+"190";
        String dateTo = "&outboundDepartureDate="+date;
        String returnDate = "&inboundDepartureDate=";
        String numberOfPassengers = "&passengerCount=1";
        String transportType = "&transportType=1";
        String notUsed = "&concessionCount=0&nusCount=0&outboundWheelchairSeated=0&outboundOtherDisabilityCount=0&inboundWheelchairSeated=0&inboundOtherDisabilityCount=0&outboundPcaCount=0&inboundPcaCount=0&promotionCode=&withReturn=0";

        String megabusUrl = PATHMEGABUS;
        megabusUrl = megabusUrl + from + to + dateTo + returnDate + numberOfPassengers + transportType;

        WebResource webResource = client.resource(megabusUrl);

        ClientResponse response = webResource
                .type(CONTENTTYPE)
                .accept(CONTENTTYPE)
                .get(ClientResponse.class);

        if (response.getClientResponseStatus() == ClientResponse.Status.OK)
        {
            String xPathPrice = "//ul[contains(@id,'JourneyResylts_OutboundList_GridViewResults')]/li[@class='five']/p";
            String xPathDeparture = "//ul[contains(@id,'JourneyResylts_OutboundList_GridViewResults')]/li[@class='two']/p[strong[.='Abfahrt']]";
            String xPathArrival = "//ul[contains(@id,'JourneyResylts_OutboundList_GridViewResults')]/li[@class='two']/p[@class='arrive']";

            String responseStr = response.getEntity(String.class);

            MegabusParser parser = new MegabusParser();
            route =  parser.parse(responseStr, route, "MegaBus", xPathPrice, xPathDeparture, xPathArrival, "euro");

        }
    }



    public Route getPolskibus(Route route, String to, String from) throws Exception {


        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String date = formatter.format(route.getDateOfTrip());

        //send request to receive SessionId
        WebResource webResource = createWebResource("https://booking.polskibus.com/Pricing/Selections?lang=PL");
        ClientResponse response = sendGetRequest(webResource, CONTENTTYPE);
        String cookieValue = getCookieForPolskiBus(response);

        //Map for PolskiBus request
        MultivaluedMap map = new MultivaluedMapImpl();
        map.add("PricingForm.Adults", "1");
        map.add("PricingForm.ConcessionCod...", "");
        map.add("PricingForm.DBType", "MY");
        map.add("PricingForm.FromCity", from);
        map.add("PricingForm.OutDate", date);
        map.add("PricingForm.PromoCode", "");
        map.add("PricingForm.RetDate", "");
        map.add("PricingForm.ToCity", to);
        map.add("PricingForm.hidSessionID", "");
        map.add("Pricingform.hidLang", "PL");
        map.add("Pricingform.hidPC", "");
        map.add("__VIEWSTATE", "/wEPDwUJNzQxODA1MTQ4DxYCHhNWYWxpZGF0ZVJlcXVlc3RNb2RlAgFkZFPllo0+VPoB1LdmTlXTzZLAiP/sLBV1dT50WMo4RYHt");
        map.add("__VIEWSTATEGENERATOR", "92D95504");

        //send request for get temporary link with prices
        WebResource webResource2 = createWebResource("https://booking.polskibus.com/Pricing/GetPrice");
        Cookie cookie = new Cookie("ASP.NET_SessionId", cookieValue);
        ClientResponse response2 = sendWidePostRequest(webResource2, cookie, "application/x-www-form-urlencoded", map);
        String locationFromHeader = getLocationFromHeader(response2);

        //send request using temporary link
        WebResource webResource3 = createWebResource("https://booking.polskibus.com" + locationFromHeader );
        ClientResponse response3 = sendGetRequestWithCookie(webResource3, CONTENTTYPE, cookie);
        String responseStr = response3.getEntity(String.class);

        //XPathes for parser
        String xPathPrice = "//div[@class='onb_resultRow']//p[@class='priceHilite']";
        String xPathDeparture = "//div[@class='onb_resultRow']//div[@class='onb_col onb_two']//p[position() mod 2 = 1]/b";
        String xPathArrival = "//div[@class='onb_resultRow']//div[@class='onb_col onb_two']//p[position() mod 2 = 0]/b";

        PolskiBusParser parser = new PolskiBusParser();
        route =  parser.parse(responseStr, route, "PolskiBus", xPathPrice, xPathDeparture, xPathArrival, "zl");
        return route;
    }


//    public void printRouteWithDetails(Route r){
//        System.out.println("From:" + r.getFrom() + " To:" + r.getTo() + " Date:" + r.getDateOfTrip() + "\nMin.Price = "
//                + r.getMinPrice());
//        for (RouteDetails node: r.getDetails()) {
//            System.out.println("Price:"+ node.getPrice()+ node.getCurrency() + " Departure:" + node.getTimeDeparture() + " Arrival:" + node.getTimeArrival() + " Company:" + node.getCompany() + "");
//        }
//    }
}
