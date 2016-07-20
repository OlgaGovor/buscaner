import com.buscanner.Route;
import com.buscanner.destinations.PolskiBusDestinationsGetter;
import com.buscanner.getDataOfRoute.GetDataLuxexpress;
import com.buscanner.getDataOfRoute.GetDataPolskiBus;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created by Olga_Govor on 7/8/2016.
 */
public class ConnectionOfCompanies {

    @Test
    public void getPrice() throws Exception {
        GetDataPolskiBus dataPolskiBus = new GetDataPolskiBus();
        GetDataLuxexpress dataLuxexpress = new GetDataLuxexpress();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date()); // Now use today date.

        String to = "";
        String from = "";

        PolskiBusDestinationsGetter getPolskiBusDestinations = new PolskiBusDestinationsGetter();
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
            route = dataPolskiBus.getData(route, to, from);

            to = "krakow";
            from = "vienna-stadion-center";
            route = dataLuxexpress.getData(route, to, from);

            System.out.println("BY DEPARTURE");
            route.sortByDeparture();
            route.printRouteWithDetails();

            System.out.println("BY PRICE");
            route.sortByPrice();
            route.printRouteWithDetails();
        }



    }

}
