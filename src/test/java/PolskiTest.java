import com.buscanner.Route;
import com.buscanner.destinations.PolskiBusDestinationsGetter;
import com.buscanner.getDataOfRoute.GetDataPolskiBus;
import org.junit.Test;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created by Olga_Govor on 6/30/2016.
 */
public class PolskiTest {

    @Test
    public void getPrice() throws Exception {
        GetDataPolskiBus dataPolskiBus = new GetDataPolskiBus();
//        BaseSendRequest sendRest = new BaseSendRequest();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date()); // Now use today date.

        String to = "";
        String from = "";

        PolskiBusDestinationsGetter getPolskiBusDestinations = new PolskiBusDestinationsGetter();
        Map<String, String> listOfDestinations = getPolskiBusDestinations.getDestinations();

//        to = listOfDestinations.get(route.getTo());
//        from = listOfDestinations.get(route.getFrom());


        c.add(Calendar.DATE, 10);
        for(int i=0; i<5 ; i++){

            Route route = new Route();


            //just for printing
            route.setFrom("Krakow");
            from = listOfDestinations.get("krak&oacute;w");


            //just for printing
            route.setTo("Vienna");
            to = listOfDestinations.get("wiedeń");

            route.setMinPrice(10000000.0);


            c.add(Calendar.DATE, 1); // Adding 5 days
            String d = sdf.format(c.getTime());
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date = formatter.parse(d);
            route.setDateOfTrip(date);

            route = dataPolskiBus.getData(route, to, from);
            route.sortByPrice();
            route.printRouteWithDetails();
        }
    }

    @Test
    public void getDestinations() throws ParserConfigurationException, XPathExpressionException {
        PolskiBusDestinationsGetter getPolskiBusDestinations = new PolskiBusDestinationsGetter();
        Map<String, String> listOfDestinations = getPolskiBusDestinations.getDestinations();
    }
}
