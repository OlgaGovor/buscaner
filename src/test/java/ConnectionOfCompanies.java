import com.buscanner.Route;
import com.buscanner.destinations.GetPolskiBusDestinations;
import com.buscanner.outRest.SendRest;
import org.junit.Test;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created by Olga_Govor on 7/8/2016.
 */
public class ConnectionOfCompanies {

    @Test
    public void getPrice() throws XPathExpressionException, ParserConfigurationException, ParseException {
        SendRest sendRest = new SendRest();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date()); // Now use today date.

        String to = "";
        String from = "";

        GetPolskiBusDestinations getPolskiBusDestinations = new GetPolskiBusDestinations();
        Map<String, String> listOfDestinations = getPolskiBusDestinations.getDestinations();

//        Get destinations from list
//        to = listOfDestinations.get(route.getTo());
//        from = listOfDestinations.get(route.getFrom());


        c.add(Calendar.DATE, 3); //start from today+10days
        for(int i=0; i<10 ; i++){
            //new date new route
            Route route = new Route();
            route.setFrom("krakow");
            route.setTo("vienna");
            route.setMinPrice(10000000.0);


            c.add(Calendar.DATE, 1); // Adding 1 day
            String d = sdf.format(c.getTime());
            Date date = sdf.parse(d);
            route.setDateOfTrip(date);

            to = listOfDestinations.get("wiedeń");
            from = listOfDestinations.get("krak&oacute;w");
            route = sendRest.getPolskibus(route, to, from);

            to = "krakow";
            from = "vienna-stadion-center";
            route = sendRest.getLuxexpress(route, to, from);

            System.out.println("BY DEPARTURE");
            route.sortByDeparture();
            route.printRouteWithDetails();

            System.out.println("BY PRICE");
            route.sortByPrice();
            route.printRouteWithDetails();
        }



    }

}
