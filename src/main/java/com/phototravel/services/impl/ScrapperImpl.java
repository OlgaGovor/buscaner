package com.phototravel.services.impl;

import com.phototravel.RequestSender;
import com.phototravel.controllers.entity.RequestForm;
import com.phototravel.entity.City;
import com.phototravel.entity.Destination;
import com.phototravel.entity.Price;
import com.phototravel.entity.Route;
import com.phototravel.repositories.DestinationRepository;
import com.phototravel.repositories.PriceRepository;
import com.phototravel.services.CityService;
import com.phototravel.services.Fetcher;
import com.phototravel.services.PriceService;
import com.phototravel.services.RouteService;
import com.phototravel.services.Scrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Olga_Govor on 8/16/2016.
 */
public class ScrapperImpl implements Scrapper {

    static final Integer LUX_EXPRESS_ID = 2;
    static final Integer POLSKI_BUS_ID = 1;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Map<Integer, Fetcher> fetchers = new HashMap<>();

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    PriceService priceService;

    @Autowired
    private DestinationRepository destinationRepository;

    @Autowired
    private RouteService routeService;

    @Autowired
    private CityService cityService;

    @Autowired
    private RequestSender requestSender;

    @Override
    public void register(Integer companyId, Fetcher fetcher) {
        fetchers.put(companyId, fetcher);
    }

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Override
    public void scrapForDay(Integer companyId, String from, String to, LocalDate date) {
        City fromCityObj = cityService.findCityByName(from);
        if (fromCityObj == null) {
            throw new IllegalArgumentException("No FROM City found in city table - " + from);
        }
        Integer fromCityId = fromCityObj.getCityId();
        City toCityObj = cityService.findCityByName(to);
        if (toCityObj == null) {
            throw new IllegalArgumentException("No TO City found in city table - " + to);
        }
        Integer toCityId = toCityObj.getCityId();

        scrapForDay(companyId, fromCityId, toCityId, date);
    }

    @Override
    public void scrapForDay(Integer companyId, int fromId, int toId, LocalDate date) {
        List<Route> routesList = routeService.getRoutesByCitiesIdsAndCompany(fromId, toId, companyId);

        for (Route route : routesList) {
            scrapRouteForDate(route, date);
        }
    }

    @Override
    public void scrapForPeriod(Integer companyId, String from, String to, LocalDate date1, LocalDate date2) {

        LocalDate dateOfTrip = date1;

        while(dateOfTrip.isBefore(date2.plusDays(1))){
            scrapForDay(companyId, from, to, dateOfTrip);
            dateOfTrip = dateOfTrip.plusDays(1);
        }
    }

    @Override
    public void scrapForPeriod(Integer companyId, int fromId, int toId, LocalDate date1, LocalDate date2) {

        LocalDate dateOfTrip = date1;

        while(dateOfTrip.isBefore(date2.plusDays(1))){
            scrapForDay(companyId, fromId, toId, dateOfTrip);
            dateOfTrip = dateOfTrip.plusDays(1);
        }
    }

    @Override
    public void scrapRouteForPeriod(Route route, LocalDate startDate, LocalDate endDate) {

        LocalDate dateOfTrip = startDate;

        while (dateOfTrip.isBefore(endDate.plusDays(1))) {
            scrapRouteForDate(route, dateOfTrip);
            dateOfTrip = dateOfTrip.plusDays(1);
        }
    }


    @Override
    public void scrapRouteForDate(Route route, LocalDate date) {

        Integer fromDestId = route.getFromDestinationId();
        Integer toDestId = route.getToDestinationId();

        Destination fromDestination = destinationRepository.findOne(fromDestId);
        Destination toDestination = destinationRepository.findOne(toDestId);
        String fromRequestValue = fromDestination.getRequestValue();
        String toRequestValue = toDestination.getRequestValue();

        Fetcher fetcher = fetchers.get(route.getCompanyId());

        List<Price> listOfPrices = fetcher.fetch(fromRequestValue, toRequestValue, date, route.getRouteId());

        Date departureDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());

        //priceRepository.movePriceToArchive(route.getRouteId(), departureDate);

        //priceRepository.deleteAllByIdRouteIdAndIdDepartureDate(route.getRouteId(), departureDate);
        priceService.movePriceToArchive(route.getRouteId(), departureDate);


        for (Price p : listOfPrices) {
            priceRepository.save(p);
            logger.info("save price=" + p);
        }

    }

    @Override
    public void scrapAllForDay(int fromId, int toId, LocalDate date)
    {
        scrapForDay(LUX_EXPRESS_ID, fromId, toId, date);
        scrapForDay(POLSKI_BUS_ID, fromId, toId, date);
    }

    @Override
    public void scrapAllForDay(String from, String to, LocalDate date)
    {
        scrapForDay(LUX_EXPRESS_ID, from, to, date);
        scrapForDay(POLSKI_BUS_ID, from, to, date);
    }

    @Override
    public void scrapAllForPeriod(int fromId, int toId, LocalDate date1, LocalDate date2){
        scrapForPeriod(LUX_EXPRESS_ID, fromId, toId, date1, date2);
        scrapForPeriod(POLSKI_BUS_ID, fromId, toId, date1, date2);
    }

    @Override
    public void scrapAllForPeriod(String from, String to, LocalDate date1, LocalDate date2){
        scrapForPeriod(LUX_EXPRESS_ID, from, to, date1, date2);
        scrapForPeriod(POLSKI_BUS_ID, from, to, date1, date2);
    }

    @Override
    public void scrapForRequestForm(RequestForm requestForm) {
        if (!requestForm.isScanForPeriod()) {
            LocalDate date = LocalDate.parse(requestForm.getDepartureDate(), formatter);
            scrapAllForDay(requestForm.getFromCity(), requestForm.getToCity(), date);
        } else {

            LocalDate date = LocalDate.parse(requestForm.getDepartureDate(), formatter);
            LocalDate dateEnd = LocalDate.parse(requestForm.getDepartureDate(), formatter);
            scrapAllForPeriod(requestForm.getFromCity(), requestForm.getToCity(), date, dateEnd);
        }

    }
}
