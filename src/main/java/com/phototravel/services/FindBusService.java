package com.phototravel.services;

import com.phototravel.controllers.entity.RequestForm;
import com.phototravel.entity.Price;
import com.phototravel.entity.ResultDetails;
import com.phototravel.entity.Route;
import com.phototravel.repositories.CompanyRepository;
import com.phototravel.repositories.PriceRepository;
import com.phototravel.repositories.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by PBezdienezhnykh on 026 26.7.2016.
 */
@Service
public class FindBusService {

    @Autowired
    PriceRepository priceRepository;


    public List<ResultDetails> findBus(RequestForm requestForm) {
        //System.out.println("findBus=" + requestForm);

        Date endDate =
                requestForm.isScanForPeriod() ? requestForm.getDepartureEndAsDate() : requestForm.getDepartureAsDate();

        List<Price> prices = priceRepository.findBusByRequestForm(requestForm.getFromCity(), requestForm.getToCity(),
                requestForm.getDepartureAsDate(), endDate);

        List<ResultDetails> resultDetailsList = transferDataToWebView(prices);
        return resultDetailsList;

    }

    public List<ResultDetails> findBusForPeriod(RequestForm requestForm) {

        Date endDate =
                requestForm.isScanForPeriod() ? requestForm.getDepartureEndAsDate() : requestForm.getDepartureAsDate();

        int from =requestForm.getFromCity();
        int to = requestForm.getToCity();
        Date d1 = requestForm.getDepartureAsDate();
        Date d2 = endDate;

        List<Price> prices = priceRepository.findCheapestBusByRequestForm(from, to, d1, d2);
        List<ResultDetails> resultDetailsList = transferDataToWebView(prices);

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
            resultDetails.setLastUpdate(price.getLastUpdate());
            resultDetails.setLink("LINK");

            resultDetailsList.add(resultDetails);
        }
        return resultDetailsList;
    }
}
