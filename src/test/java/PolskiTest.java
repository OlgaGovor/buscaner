import com.buscanner.Route;
import com.buscanner.destinations.PolskiBusDestinationsGetter;
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
 * Created by Olga_Govor on 6/30/2016.
 */
public class PolskiTest {

    @Test
    public void getPrice() throws XPathExpressionException, ParserConfigurationException, ParseException {
        SendRest sendRest = new SendRest();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date()); // Now use today date.

        String to = "";
        String from = "";

        PolskiBusDestinationsGetter getPolskiBusDestinations = new PolskiBusDestinationsGetter();
        Map<String, String> listOfDestinations = getPolskiBusDestinations.getDestinations();

//        to = listOfDestinations.get(route.getTo());
//        from = listOfDestinations.get(route.getFrom());

        to = listOfDestinations.get("wiedeń");
        from = listOfDestinations.get("krak&oacute;w");


        c.add(Calendar.DATE, 10);
        for(int i=0; i<5 ; i++){

            Route route = new Route();
            //route.setFrom("warsaw-centralny");
            route.setFrom("krak&oacute;w");
//        route.setTo("budapest-nepliget-metro-station");
            route.setTo("wiedeń");
            route.setMinPrice(10000000.0);


            c.add(Calendar.DATE, 1); // Adding 5 days
            String d = sdf.format(c.getTime());
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date = formatter.parse(d);
            route.setDateOfTrip(date);

            route = sendRest.getPolskibus(route, to, from);
            sendRest.printRouteWithDetails(route);
        }
    }

    @Test
    public void getDestinations() throws ParserConfigurationException, XPathExpressionException {
        PolskiBusDestinationsGetter getPolskiBusDestinations = new PolskiBusDestinationsGetter();
        Map<String, String> listOfDestinations = getPolskiBusDestinations.getDestinations();
    }
}
