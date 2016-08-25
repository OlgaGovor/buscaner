package com.phototravel;

import com.phototravel.services.Scrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by Olga_Govor on 7/8/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BuscanerTestApplication.class)
@WebAppConfiguration
public class ConnectionOfCompanies {

    static final Integer LUX_EXPRESS_ID = 2;
    static final Integer POLSKI_BUS_ID = 1;

    @Autowired
    Scrapper scrapper;

    @Test
    public void getPriceForDateAndDirections() throws java.text.ParseException {
        String d = "17/09/2016";
        String from = "Krakow";
        String to = "Warszawa";


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(d, formatter);


        scrapper.scrapForDay(LUX_EXPRESS_ID, from, to, date);
        scrapper.scrapForDay(POLSKI_BUS_ID, from, to, date);
    }

    @Test
    public void getPriceForPeriod() throws Exception {
        String d1 = "01/10/2016";
        String d2 = "03/10/2016";
        String from = "Warszawa";
        String to = "Krakow";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date1 = LocalDate.parse(d1, formatter);
        LocalDate date2 = LocalDate.parse(d2, formatter);

        scrapper.scrapForPeriod(LUX_EXPRESS_ID, from, to, date1, date2);
        scrapper.scrapForPeriod(POLSKI_BUS_ID, from, to, date1, date2);
    }


}
