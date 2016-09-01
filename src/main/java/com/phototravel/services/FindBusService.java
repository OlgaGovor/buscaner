package com.phototravel.services;

import com.phototravel.controllers.entity.PriceCalendar;
import com.phototravel.controllers.entity.RequestForm;
import com.phototravel.entity.Price;
import com.phototravel.entity.ResultDetails;
import com.phototravel.entity.Route;
import com.phototravel.repositories.CompanyRepository;
import com.phototravel.repositories.PriceRepository;
import com.phototravel.repositories.RouteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    public List<ResultDetails> findBus(RequestForm requestForm) {
        logger.info("findBus");
        //System.out.println("findBus=" + requestForm);

        Date endDate =
                requestForm.isScanForPeriod() ? requestForm.getDepartureEndAsDate() : requestForm.getDepartureAsDate();
//        Date endDate = requestForm.getDepartureEndAsDate();

        List<Price> prices = priceRepository.findBusByRequestForm(requestForm.getFromCity(), requestForm.getToCity(),
                requestForm.getDepartureAsDate(), endDate);

        if (prices.size() == 0)
        {

            LocalDate date = LocalDate.parse(requestForm.getDepartureDate(), formatter);
            scrapper.scrapAllForDay(requestForm.getFromCity(), requestForm.getToCity(), date);
            prices = priceRepository.findBusByRequestForm(requestForm.getFromCity(), requestForm.getToCity(),
                    requestForm.getDepartureAsDate(), endDate);
        }
        else {
            LocalDate dateForComparing = prices.get(0).getLastUpdate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if (dateForComparing.isBefore(LocalDate.now().minusDays(1))) {

                LocalDate date = LocalDate.parse(requestForm.getDepartureDate(), formatter);
                scrapper.scrapAllForDay(requestForm.getFromCity(), requestForm.getToCity(), date);
                prices = priceRepository.findBusByRequestForm(requestForm.getFromCity(), requestForm.getToCity(),
                        requestForm.getDepartureAsDate(), endDate);
            }
        }
        List<ResultDetails> resultDetailsList = transferDataToWebView(prices);
        sortByDepartureDate(resultDetailsList);
        return resultDetailsList;

    }

    public List<ResultDetails> findBusForPeriod(RequestForm requestForm) {
        logger.info("findBusForPeriod");

//        Date endDate =
//                requestForm.isScanForPeriod() ? requestForm.getDepartureEndAsDate() : requestForm.getDepartureAsDate();

        int from =requestForm.getFromCity();
        int to = requestForm.getToCity();
        Date d1 = requestForm.getDepartureAsDate();
        Date d2 = requestForm.getDepartureEndAsDate();

        List<PriceCalendar> prices = priceService.pricesForCalendarView(from, to, d1, d2);


        LocalDate date1 = LocalDate.parse(requestForm.getDepartureDate(), formatter);
        LocalDate date2 = LocalDate.parse(requestForm.getDepartureDateEnd(), formatter);
        Period p = date1.until(date2);

     /*   if ((p.getDays()+1) != prices.size()) {

            while (date1.isBefore(date2.plusDays(1))){

                Date dateForReq = Date.from(date1.atStartOfDay(ZoneId.systemDefault()).toInstant());
                prices = priceRepository.findBusByRequestForm(requestForm.getFromCity(), requestForm.getToCity(),
                        dateForReq, dateForReq);

                if (prices.size() == 0)
                {
                    LocalDate date = LocalDate.parse(requestForm.getDepartureDate(), formatter);
                    scrapper.scrapAllForDay(requestForm.getFromCity(), requestForm.getToCity(), date);
//                    prices = priceRepository.findBusByRequestForm(requestForm.getFromCity(), requestForm.getToCity(),
//                            dateForReq, dateForReq);
                }
                else {
                    if (prices.size()!=0) {
                        LocalDate dateForComparing = prices.get(0).getLastUpdate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        if (dateForComparing.isBefore(LocalDate.now().minusDays(1))) {
                            LocalDate date = LocalDate.parse(requestForm.getDepartureDate(), formatter);
                            scrapper.scrapAllForDay(requestForm.getFromCity(), requestForm.getToCity(), date);
//                        prices = priceRepository.findBusByRequestForm(requestForm.getFromCity(), requestForm.getToCity(),
//                                dateForReq, dateForReq);
                        }
                    }
                }
                date1 = date1.plusDays(1);
            }
//            scrapper.scrapAllForPeriod(from, to, date1, date2);
            prices = priceRepository.findCheapestBusByRequestForm(from, to, d1, d2);
        }*/

        List<ResultDetails> resultDetailsList = buildResultForCalendarView(prices);
        //sortByDepartureDate(resultDetailsList);
        return resultDetailsList;
    }


    private void findRoute(RequestForm requestForm) {

    }

    @Autowired
    RouteRepository routeRepository;

    @Autowired
    CompanyRepository companyRepository;

    public List<ResultDetails> transferDataToWebView (List<Price> prices)
    {
        List<ResultDetails> resultDetailsList = new ArrayList<ResultDetails>();


        for (Price price:prices) {
            ResultDetails resultDetails = new ResultDetails();

            resultDetails.setDepartureDate(price.getDepartureDate());
            resultDetails.setDepartureTime(price.getDepartureTime());
            resultDetails.setArrivalTime(price.getArrivalTime());

            Route route = routeRepository.getRouteByRouteId(price.getRouteId());
            Integer companyId = route.getCompanyId();
            String companyName = companyRepository.findCompanyById(companyId);
            resultDetails.setCompany(companyName);
            resultDetails.setPrice(price.getPrice());
            resultDetails.setCurrency(price.getCurrency());
            resultDetails.setLastUpdate(price.getLastUpdateString());
            resultDetails.setLink("LINK");

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
