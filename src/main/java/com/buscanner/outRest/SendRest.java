package com.buscanner.outRest;

import com.buscanner.Route;
import com.buscanner.RouteDetails;
import com.buscanner.parser.LuxexpressParser;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.text.SimpleDateFormat;

/**
 * Created by Olga_Govor on 6/29/2016.
 */
public class SendRest {

    private static final String PATH = "http://ticket.luxexpress.eu/pl/wyjazdy-harmonogram/";
    // krakow/prague?Date=7-29-2016&Currency=CURRENCY.PLN"
    private static final String CONTENTTYPE = "application/json";

    public void getLuxexpress(Route route) throws XPathExpressionException, ParserConfigurationException {

        LuxexpressParser parser = new LuxexpressParser();

        Client client = Client.create();
        //form Path from parametr route and get data from DB
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        String date = formatter.format(route.getDateOfTrip());
        WebResource webResource = client.resource(PATH + route.getFrom() + "/" + route.getTo() + "?Date=" + date +"&Currency=CURRENCY.PLN");

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

    public void printRouteWithDetails(Route r){
        System.out.println("From:" + r.getFrom() + " To:" + r.getTo() + " Date:" + r.getDateOfTrip());
        for (RouteDetails node: r.getDetails()) {
            System.out.println("Price:"+ node.getPrice()+ " Departure:" + node.getTimeDeparture() + " Arrival:" + node.getTimeArrival() + " Company:" + node.getCompany() + "");
        }
    }
}
