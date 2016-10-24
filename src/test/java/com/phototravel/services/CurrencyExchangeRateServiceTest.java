package com.phototravel.services;

import com.phototravel.BuscanerTestApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BuscanerTestApplication.class)
@WebAppConfiguration
public class CurrencyExchangeRateServiceTest {


    @Autowired
    CurrencyExchangeRateService exchangeRateService;

    @Test
    public void getRate() throws Exception {
        double rateToPLN = exchangeRateService.getRate("EUR", "PLN");
        System.out.println("rateEURToPLN=" + rateToPLN);
        Assert.assertTrue(rateToPLN > 0);
        Assert.assertTrue(rateToPLN < 10);

    }

    @Test
    public void getRateToEUR() throws Exception {
        double rateToPLN = exchangeRateService.getRateToEUR("PLN");
        System.out.println("rateToPLN=" + rateToPLN);
        Assert.assertTrue(rateToPLN > 0);
        Assert.assertTrue(rateToPLN < 10);
    }

}