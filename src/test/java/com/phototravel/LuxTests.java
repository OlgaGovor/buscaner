package com.phototravel;

import com.phototravel.dataCollectors.LuxexpressCollector;
import com.phototravel.dataCollectors.Route;
import com.phototravel.dataCollectors.destinations.LuxexpressDestinationGetter;
import org.json.JSONException;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Olga_Govor on 6/29/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BuscanerApplication.class)
@WebAppConfiguration
public class LuxTests {

    @Autowired
    LuxexpressCollector luxexpressCollector;

    @Autowired
    LuxexpressDestinationGetter luxexpressDestinationGetter;

    @Test
    public void getPriceForDateAndDirection() throws Exception {

        String d = "20/08/2016";
        String from = "Krakow";
        String to = "Vienna";

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = formatter.parse(d);

        Route route = new Route(from, to, date);
        route = luxexpressCollector.getPriceForDate(route);

        route.printRouteWithDetails();

    }

    @Test
    public void getPriceForPeriodAndDirections() throws Exception {
        String d1 = "13/08/2016";
        String d2 = "17/08/2016";
        String from = "Krakow";
        String to = "Vienna";

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = formatter.parse(d1);
        Date date2 = formatter.parse(d2);

        Route route = new Route(from, to);
        List<Route> routeList = luxexpressCollector.getPriceForPeriod(route, date1, date2);
        for (Route r: routeList) {
            r.printRouteWithDetails();
        }
    }

    @Test
    public void getDestinations() throws ParserConfigurationException, XPathExpressionException, JSONException, ParseException {

        Map<String, String> listOfDestinations = luxexpressDestinationGetter.getDestinations();
    }

}
