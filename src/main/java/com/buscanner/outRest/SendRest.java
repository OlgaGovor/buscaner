package com.buscanner.outRest;

import com.buscanner.Route;
import com.buscanner.RouteDetails;
import com.buscanner.parser.LuxexpressParser;
import com.buscanner.parser.MegabusParser;
import com.buscanner.parser.PolskiBusParser;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Olga_Govor on 6/29/2016.
 */
public class SendRest {

    private static final String PATHLUX = "http://ticket.luxexpress.eu/pl/wyjazdy-harmonogram/";
    private static final String PATHPOLSKIBUS = "https://booking.polskibus.com/Pricing/Selections?lang=PL";
    // krakow/prague?Date=7-29-2016&Currency=CURRENCY.PLN"
    private static final String CONTENTTYPE = "application/json";

    public String sendRequest(Route route, String resource, String date){

        Client client = Client.create();
        WebResource webResource = client.resource(resource);

        ClientResponse response = webResource
                .type(CONTENTTYPE)
                .accept(CONTENTTYPE)
                .get(ClientResponse.class);

        if (response.getClientResponseStatus() == ClientResponse.Status.OK) {
            String responseStr = response.getEntity(String.class);
            return  responseStr;
        }
        return null;
    }

    public void getLuxexpress(Route route) throws XPathExpressionException, ParserConfigurationException, ParseException {

        String xPathPrice = "//div[contains (@class, 'regular-fullPrice')]//span[@class = 'amount']";
        String xPathDeparture = "//div[contains(@class,'row times')]/div/span[1]";
        String xPathArrival = "//div[contains(@class,'row times')]/div/span[2]";

        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        String date = formatter.format(route.getDateOfTrip());
        String url = PATHLUX + route.getFrom() + "/" + route.getTo() + "?Date=" + date +"&Currency=CURRENCY.PLN";

        String response = sendRequest(route, url, date);

        LuxexpressParser parser = new LuxexpressParser();

        route =  parser.parse(response, route, "LuxExpress", xPathPrice, xPathDeparture, xPathArrival, "zl");
        printRouteWithDetails(route);

    }


    public void getMegabus(Route route) throws XPathExpressionException, ParserConfigurationException, ParseException {
        //uk. for pounds
        String megabusUrl = "http://deeu.megabus.com/JourneyResults.aspx?";

        MegabusParser parser = new MegabusParser();

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

        megabusUrl = megabusUrl +from+to+dateTo+returnDate+numberOfPassengers+transportType;

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
            route =  parser.parse(responseStr, route, "MegaBus", xPathPrice, xPathDeparture, xPathArrival, "euro");

            printRouteWithDetails(route);

        }
    }


    public void getPolskibus(Route route, String to, String from) throws XPathExpressionException, ParserConfigurationException, ParseException {

        Client client = Client.create();
        client.setFollowRedirects(false);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String date = formatter.format(route.getDateOfTrip());

        WebResource webResource = client.resource("https://booking.polskibus.com/Pricing/Selections?lang=PL");

        ClientResponse response = webResource.get(ClientResponse.class);

        String headerStr = "";

        if (response.getClientResponseStatus() == ClientResponse.Status.OK)
        {
            MultivaluedMap<String, String> headers = response.getHeaders();
            headerStr = headers.get("Set-Cookie").toString();
        }

        String cookieValue = headerStr.substring(19,43);

        WebResource webResource2 = client.resource("https://booking.polskibus.com/Pricing/GetPrice");

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

        Cookie cookie = new Cookie("ASP.NET_SessionId", cookieValue);

        ClientResponse response2 = webResource2
                .cookie(cookie)
                .type("application/x-www-form-urlencoded")
                .post(ClientResponse.class, map);


        MultivaluedMap<String, String> headers = response2.getHeaders();
        headerStr = headers.get("Location").toString();
        headerStr = headerStr.substring(1, headerStr.length()-1);

        WebResource webResource3 = client.resource("https://booking.polskibus.com" + headerStr);

        ClientResponse response3 = webResource3
                .cookie(cookie)
                .get(ClientResponse.class);

        String responseStr = response3.getEntity(String.class);

        String xPathPrice = "//div[@class='onb_resultRow']//p[@class='priceHilite']";
        String xPathDeparture = "//div[@class='onb_resultRow']//div[@class='onb_col onb_two']//p[position() mod 2 = 1]/b";
        String xPathArrival = "//div[@class='onb_resultRow']//div[@class='onb_col onb_two']//p[position() mod 2 = 0]/b";

        PolskiBusParser parser = new PolskiBusParser();
        route =  parser.parse(responseStr, route, "PolskiBus", xPathPrice, xPathDeparture, xPathArrival, "zl");

        printRouteWithDetails(route);

    }


    public void printRouteWithDetails(Route r){
        System.out.println("From:" + r.getFrom() + " To:" + r.getTo() + " Date:" + r.getDateOfTrip() + "\nMin.Price = "
                + r.getMinPrice());
        for (RouteDetails node: r.getDetails()) {
            System.out.println("Price:"+ node.getPrice()+ node.getCurrency() + " Departure:" + node.getTimeDeparture() + " Arrival:" + node.getTimeArrival() + " Company:" + node.getCompany() + "");
        }
    }
}
