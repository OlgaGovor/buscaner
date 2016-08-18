package com.phototravel.outerRequests;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MultivaluedMap;

/**
 * Created by Olga_Govor on 6/29/2016.
 */
public class BaseSendRequest {


    private static final String PATHMEGABUS = "http://deeu.megabus.com/JourneyResults.aspx?";

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

    public String getCookie (ClientResponse response)
    {
        String headerStr = "";
        if (response.getClientResponseStatus() == ClientResponse.Status.OK)
        {
            MultivaluedMap<String, String> headers = response.getHeaders();
            headerStr = headers.get("Set-Cookie").toString();
        }

        return headerStr;
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


//    public Route getMegabus(Route route) throws Exception {
//
//        Client client = Client.create();
//        //form Path from parametr route and get data from DB
//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//        String date = formatter.format(route.getDateOfTrip());
//        //Parameters
//        String from = "originCode="+"197";
//        String to = "&destinationCode="+"190";
//        String dateTo = "&outboundDepartureDate="+date;
//        String returnDate = "&inboundDepartureDate=";
//        String numberOfPassengers = "&passengerCount=1";
//        String transportType = "&transportType=1";
//        String notUsed = "&concessionCount=0&nusCount=0&outboundWheelchairSeated=0&outboundOtherDisabilityCount=0&inboundWheelchairSeated=0&inboundOtherDisabilityCount=0&outboundPcaCount=0&inboundPcaCount=0&promotionCode=&withReturn=0";
//
//        String megabusUrl = PATHMEGABUS;
//        megabusUrl = megabusUrl + from + to + dateTo + returnDate + numberOfPassengers + transportType;
//
//        WebResource webResource = client.resource(megabusUrl);
//
//        ClientResponse response = webResource
//                .type(CONTENTTYPE)
//                .accept(CONTENTTYPE)
//                .get(ClientResponse.class);
//
//        if (response.getClientResponseStatus() == ClientResponse.Status.OK)
//        {
//            String xPathPrice = "//ul[contains(@id,'JourneyResylts_OutboundList_GridViewResults')]/li[@class='five']/p";
//            String xPathDeparture = "//ul[contains(@id,'JourneyResylts_OutboundList_GridViewResults')]/li[@class='two']/p[strong[.='Abfahrt']]";
//            String xPathArrival = "//ul[contains(@id,'JourneyResylts_OutboundList_GridViewResults')]/li[@class='two']/p[@class='arrive']";
//
//            String responseStr = response.getEntity(String.class);
//
//            MegabusParser parser = new MegabusParser();
//            route =  parser.parse(responseStr, route, "MegaBus", xPathPrice, xPathDeparture, xPathArrival, "euro");
//        }
//
//        return route;
//    }

}
