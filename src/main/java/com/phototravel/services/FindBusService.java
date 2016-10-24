package com.phototravel.services;

import com.phototravel.controllers.entity.PriceCalendar;
import com.phototravel.controllers.entity.RequestForm;
import com.phototravel.controllers.entity.ResultDetails;
import com.phototravel.entity.Company;
import com.phototravel.entity.Price;
import com.phototravel.entity.Route;
import com.phototravel.repositories.PriceRepository;
import com.phototravel.services.companiesConfig.Config;
import com.phototravel.services.companiesConfig.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by PBezdienezhnykh on 026 26.7.2016.
 */
@Service
public class FindBusService {
    @Autowired
    Scrapper scrapper;

    @Autowired
    PriceRepository priceRepository;

    @Autowired
    PriceService priceService;

    @Autowired
    RouteService routeService;

    @Autowired
    DestinationService destinationService;

    @Autowired
    CompanyService companyService;

    @Autowired
    protected ConfigFactory configFactory;

    @Autowired
    CurrencyExchangeRateService currencyExchangeRateService;

    private static final String dbCurrency = "EUR";

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    public List<ResultDetails> findBus(RequestForm requestForm) {
        if (requestForm.isScanForPeriod()) {
            return findBus(requestForm, ResultDetails.ViewType.CALENDAR);
        } else {
            return findBus(requestForm, ResultDetails.ViewType.DAILY);
        }
    }

    public List<ResultDetails> findBus(RequestForm requestForm, ResultDetails.ViewType viewType) {
        logger.info("findBus: " + requestForm);

        if (viewType == ResultDetails.ViewType.CALENDAR) {
            requestForm.setDepartureDate(requestForm.getDepartureDateAsLocalDate().withDayOfMonth(1));
            LocalDate departureDateEnd = requestForm.getDepartureDateEndAsLocalDate();
            requestForm.setDepartureDateEnd(departureDateEnd.withDayOfMonth(departureDateEnd.lengthOfMonth()));
            return findBusForPeriod(requestForm, viewType);
        } else if (viewType == ResultDetails.ViewType.DATE_SLIDER) {
            return findBusForPeriod(requestForm, viewType);
        } else {
            return findBusForDay(requestForm);
        }

    }

    private List<ResultDetails> findBusForDay(RequestForm requestForm) {
        logger.info("findBusForDay");

        List<Price> prices = priceRepository.findBusForDay(requestForm.getFromCity(), requestForm.getToCity(),
                requestForm.getDepartureAsDate());
        logger.info("findBusForDay - done, found: " + prices.size());

        if (prices == null || prices.isEmpty()) {
            logger.info("call scrapper");
            scrapper.scrapForRequestForm(requestForm);
            prices = priceRepository.findBusForDay(requestForm.getFromCity(), requestForm.getToCity(),
                    requestForm.getDepartureAsDate());
            logger.info("findBusForDay - after scrapping, found: " + prices.size());
        }

        double rateToEUR = currencyExchangeRateService.getRateToEUR(requestForm.getCurrency());
        for (Price price : prices) {
            double newPrice = price.getPrice() * rateToEUR;
            newPrice = Math.round(newPrice * 100) / 100.;
            price.setPrice(newPrice);
            price.setCurrency(requestForm.getCurrency());
        }

        List<ResultDetails> resultDetailsList = buildResultForDayView(prices);

        return resultDetailsList;
    }


    private List<ResultDetails> findBusForPeriod(RequestForm requestForm, ResultDetails.ViewType viewType) {
        logger.info("findBusForPeriod");

        int fromCityId = requestForm.getFromCity();
        int toCityId = requestForm.getToCity();
        Date startDate = requestForm.getDepartureAsDate();
        Date endDate = requestForm.getDepartureEndAsDate();

        List<PriceCalendar> prices = priceService.pricesForCalendarView(fromCityId, toCityId, startDate, endDate);

        double rateToEUR = currencyExchangeRateService.getRateToEUR(requestForm.getCurrency());
        for (PriceCalendar price : prices) {
            double newPrice = price.getPrice() * rateToEUR;
            newPrice = Math.round(newPrice * 100) / 100.;
            price.setPrice(newPrice);
            price.setCurrency(requestForm.getCurrency());
        }

        if (viewType == ResultDetails.ViewType.DATE_SLIDER) {
            return buildResultForDateSliderView(requestForm, prices);
        } else {
            return buildResultForCalendarView(prices);
        }
    }


    public List<ResultDetails> buildResultForDayView(List<Price> prices) {
        List<ResultDetails> resultDetailsList = new ArrayList<ResultDetails>();

        for (Price price : prices) {
            ResultDetails resultDetails = new ResultDetails();

            Route route = routeService.getRouteByRouteId(price.getRouteId());
            String fromDestination = destinationService.getDestinationNameByDestinationId(route.getFromDestinationId());
            String toDestination = destinationService.getDestinationNameByDestinationId(route.getToDestinationId());
            Company company = companyService.findCompanyById(route.getCompanyId());
            //--TODO: correct and working link
            Config companyConfig = configFactory.getConfig(company.getCompanyName() + "Config");
            String url = companyConfig.getLink(route, price.getDepartureDateString()); //scrapper.getLink(route, price.getDepartureDateString());
            // logger.info(url);
            resultDetails.setLink(url);
            //------------------------


            resultDetails.setFromDestination(fromDestination);
            resultDetails.setToDestination(toDestination);

            resultDetails.setDepartureDate(price.getDepartureDateString());
            resultDetails.setDepartureTime(price.getDepartureTime());
            resultDetails.setArrivalTime(price.getArrivalTime());

            resultDetails.setCompany(company.getCompanyName());

            //TODO: convert in user currency
            resultDetails.setPrice(price.getPrice());
            resultDetails.setCurrency(price.getCurrency());

            //-------------------------
            resultDetails.setLastUpdate(price.getLastUpdateString());
            resultDetails.setDuration(price.getDuration());

            resultDetailsList.add(resultDetails);
        }
        return resultDetailsList;
    }

    public List<ResultDetails> buildResultForCalendarView(List<PriceCalendar> prices) {
        List<ResultDetails> resultDetailsList = new ArrayList<ResultDetails>();

        for (PriceCalendar price : prices) {
            ResultDetails resultDetails = new ResultDetails();

            resultDetails.setDepartureDate(price.getDateAsString());
            resultDetails.setPrice(price.getPrice());
            resultDetails.setCurrency(price.getCurrency());

            resultDetailsList.add(resultDetails);
        }
        return resultDetailsList;
    }

    public List<ResultDetails> buildResultForDateSliderView(RequestForm requestForm, List<PriceCalendar> prices) {

        LocalDate startDate = requestForm.getDepartureDateAsLocalDate();
        LocalDate endDate = requestForm.getDepartureDateEndAsLocalDate();

        List<ResultDetails> resultDetailsList = new ArrayList<ResultDetails>();

        for (LocalDate date = startDate; date.isBefore(endDate) || date.isEqual(endDate); date = date.plusDays(1)) {
            boolean found = false;
            for (PriceCalendar price : prices) {
                if (price.getDateAsLocalDate().isEqual(date)) {
                    found = true;
                    ResultDetails resultDetails = new ResultDetails();

                    resultDetails.setDepartureDate(price.getDateAsString());
                    resultDetails.setPrice(price.getPrice());
                    resultDetails.setCurrency(price.getCurrency());

                    resultDetailsList.add(resultDetails);

                    break;
                }
            }
            if (!found) {
                ResultDetails resultDetails = new ResultDetails();
                resultDetails.setDepartureDate(date.format(formatter));

                resultDetailsList.add(resultDetails);
            }

        }

        return resultDetailsList;
    }


    public boolean checkIfRouteExists(int fromCityId, int toCityId, Boolean hasChanges) {
        List<Route> routes = routeService.findRoutesByCityIds(fromCityId, toCityId, hasChanges);
        return routes.size() > 0;
    }

    private void convertCurrency(String fromCurrency, String toCurrency, List<Price> prices) {


    }
}
