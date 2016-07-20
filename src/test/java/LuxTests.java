import com.buscanner.Route;
import com.buscanner.getDataOfRoute.GetDataLuxexpress;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Olga_Govor on 6/29/2016.
 */
public class LuxTests {

    @Test
    public void getPrice() throws Exception {
//        BaseSendRequest sendRest = new BaseSendRequest();
        GetDataLuxexpress dataLuxexpress = new GetDataLuxexpress();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date()); // Now use today date.

        for(int i=0; i<10 ; i++){

            Route route = new Route();

            //route.setFrom("warsaw-centralny");
            route.setFrom("krakow");
            String to ="krakow";
            route.setTo("budapest-nepliget-metro-station");
            String from ="budapest-nepliget-metro-station";
            //route.setTo("prague");
            route.setMinPrice(10000000.0);

            c.add(Calendar.DATE, 1); // Adding 5 days
            String d = sdf.format(c.getTime());
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date = formatter.parse(d);
            route.setDateOfTrip(date);

            route = dataLuxexpress.getData(route, to, from);
            route.printRouteWithDetails();
        }
    }



}
