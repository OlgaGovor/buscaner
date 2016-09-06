package com.phototravel.services;

import com.phototravel.controllers.entity.PriceCalendar;
import com.phototravel.controllers.entity.RequestForm;
import com.phototravel.entity.Company;
import com.phototravel.entity.Price;
import com.phototravel.entity.ResultDetails;
import com.phototravel.entity.Route;
import com.phototravel.repositories.PriceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    public List<ResultDetails> findBus(RequestForm requestForm) {
        logger.info("findBus");

        if (requestForm.isScanForPeriod()) {
            return findBusForPeriod(requestForm);
        }
        else {
            return findBusForDay(requestForm);
        }

    }

    private List<ResultDetails> findBusForDay(RequestForm requestForm) {
        logger.info("findBusForDay");

        List<Price> prices = priceRepository.findBusForDay(requestForm.getFromCity(), requestForm.getToCity(),
                requestForm.getDepartureAsDate());
        logger.info("findBusForDay - done");

        List<ResultDetails> resultDetailsList = buildResultForDayView(prices);

        return resultDetailsList;
    }


    private List<ResultDetails> findBusForPeriod(RequestForm requestForm) {
        logger.info("findBusForPeriod");

        int fromCityId = requestForm.getFromCity();
        int toCityId = requestForm.getToCity();
        Date startDate = requestForm.getDepartureAsDate();
        Date endDate = requestForm.getDepartureEndAsDate();

        List<PriceCalendar> prices = priceService.pricesForCalendarView(fromCityId, toCityId, startDate, endDate);

        List<ResultDetails> resultDetailsList = buildResultForCalendarView(prices);

        return resultDetailsList;
    }


    public List<ResultDetails> buildResultForDayView(List<Price> prices)
    {
        List<ResultDetails> resultDetailsList = new ArrayList<ResultDetails>();

        for (Price price:prices) {
            ResultDetails resultDetails = new ResultDetails();

            Route route = routeService.getRouteByRouteId(price.getRouteId());
            String fromDestination = destinationService.getDestinationNameByDestinationId(route.getFromDestinationId());
            String toDestination = destinationService.getDestinationNameByDestinationId(route.getToDestinationId());
            Company company = companyService.findCompanyById(route.getCompanyId());

            resultDetails.setFromDestination(fromDestination);
            resultDetails.setToDestination(toDestination);

            resultDetails.setDepartureDate(price.getDepartureDate());
            resultDetails.setDepartureTime(price.getDepartureTime());
            resultDetails.setArrivalTime(price.getArrivalTime());

            resultDetails.setCompany(company.getCompanyName());
            resultDetails.setPrice(price.getPrice());
            resultDetails.setCurrency(price.getCurrency());
            resultDetails.setLastUpdate(price.getLastUpdateString());
            resultDetails.setLink(company.getCompanyUrl());

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

            resultDetailsList.add(resultDetails);
        }
        return resultDetailsList;
    }

    public void sortByDepartureDate(List<ResultDetails> resultDetailsList){
        Collections.sort(resultDetailsList, new Comparator<ResultDetails>() {
            @Override
            public int compare(ResultDetails o1, ResultDetails o2) {
                return o1.getDepartureTime().compareTo(o2.getDepartureTime());
            }
        });
        System.out.println();
    }
}
