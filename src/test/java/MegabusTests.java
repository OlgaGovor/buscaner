import com.buscanner.Route;
import com.buscanner.outRest.SendRest;
import org.junit.Test;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Olga_Govor on 7/1/2016.
 */
public class MegabusTests {

    @Test
    public void getPrice() throws XPathExpressionException, ParserConfigurationException, ParseException {
        SendRest sendRest = new SendRest();
        Route route = new Route();

        //route.setFrom("warsaw-centralny");
        route.setFrom("berlin");//197
        route.setTo("munich");//190
        //route.setTo("prague");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date()); // Now use today date.

        for(int i=0; i<5 ; i++){
            c.add(Calendar.DATE, 1); // Adding 5 days
            String d = sdf.format(c.getTime());
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date = formatter.parse(d);
            route.setDateOfTrip(date);

            sendRest.getMegabus(route);
        }
    }
}