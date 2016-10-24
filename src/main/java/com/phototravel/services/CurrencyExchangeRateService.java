package com.phototravel.services;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Service
public class CurrencyExchangeRateService {

    //http://api.fixer.io/latest?base=EUR
    private Map<String, ExchangeRate> rates = new HashMap<>();

    @Value("${currencyExchange.url}")
    private String url;

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    private WebResource createWebResource(String url) {
        Client client = Client.create();
        client.setFollowRedirects(false);
        WebResource webResource = client.resource(url);
        return webResource;
    }

    private ClientResponse sendGetRequest(WebResource webResource, String dataType) {
        ClientResponse response = webResource
                .type(dataType)
                .accept(dataType)
                .get(ClientResponse.class);

        return response;
    }

    @Cacheable("exchange")
    private void getExchangeRates(String currency) throws JSONException {
        logger.info("getExchangeRates " + url + "?base=" + currency);
        WebResource webResource = createWebResource(url + "?base=" + currency);
        ClientResponse response = sendGetRequest(webResource, "application/json");
        String responseString = response.getEntity(String.class);
        logger.info("getExchangeRates responseString" + responseString);
        JSONObject json = new JSONObject(responseString);
        String base = json.getString("base");
        LocalDate date = LocalDate.parse(json.getString("date"), formatter);
        ExchangeRate exchangeRate = new ExchangeRate(base, date);
        JSONObject rates = json.getJSONObject("rates");
        Iterator iterator = rates.keys();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            double rate = rates.getDouble(key);
            exchangeRate.addRate(key, rate);
        }
        this.rates.put(exchangeRate.getBaseCurrency(), exchangeRate);

    }

    @CacheEvict("exchange")
    private void updateExchangeRates(String currency) throws JSONException {
        logger.info("updateExchangeRates " + currency);
        getExchangeRates(currency);
    }

    public double getRate(String fromCurrency, String toCurrency) {
        logger.info("getRate fromCurrency=" + fromCurrency + " toCurrency=" + toCurrency);
        if (fromCurrency.equalsIgnoreCase("EUR")) {
            return getRateToEUR(toCurrency);
        }

        return 0;
    }

    public double getRateToEUR(String currency) {
        logger.info("getRateToEUR " + currency);
        String base = "EUR";
        if (base.equals(currency)) {
            return 1;
        }
        if (!rates.containsKey(base)) {
            try {
                getExchangeRates(base);
            } catch (JSONException e) {
                e.printStackTrace();
                return 0;
            }
        }
        ExchangeRate exchangeRate = rates.get(base);

        if (exchangeRate.getDate().isBefore(LocalDate.now().minusDays(2))) {
            try {
                updateExchangeRates(currency);
            } catch (JSONException e) {
                e.printStackTrace();
                return 0;
            }
        }

        return exchangeRate.getRates().get(currency);
    }

    private class ExchangeRate {
        private String baseCurrency;
        private LocalDate date;
        private Map<String, Double> rates = new HashMap<>();

        public ExchangeRate(String baseCurrency, LocalDate date) {
            this.baseCurrency = baseCurrency;
            this.date = date;
        }

        public String getBaseCurrency() {
            return baseCurrency;
        }

        public void setBaseCurrency(String baseCurrency) {
            this.baseCurrency = baseCurrency;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public Map<String, Double> getRates() {
            return rates;
        }

        public void setRates(Map<String, Double> rates) {
            this.rates = rates;
        }

        public void addRate(String currency, Double rate) {
            this.rates.put(currency, rate);
        }

    }


}
