package com.phototravel.services.impl;

import com.phototravel.RequestSender;
import com.phototravel.entity.Destination;
import com.phototravel.entity.Price;
import com.phototravel.entity.Route;
import com.phototravel.repositories.DestinationRepositoty;
import com.phototravel.repositories.PriceRepository;
import com.phototravel.repositories.RouteRepository;
import com.phototravel.services.CityService;
import com.phototravel.services.Fetcher;
import com.phototravel.services.RouteService;
import com.phototravel.services.Scrapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Olga_Govor on 8/16/2016.
 */
public class ScrapperImpl implements Scrapper {

    private Set<Fetcher> fetchers = new HashSet<>();

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private DestinationRepositoty destinationRepositoty;

    @Autowired
    private RouteService routeService;

    @Autowired
    private CityService cityService;

    @Autowired
    private RequestSender requestSender;

    @Override
    public void register(Fetcher fetcher) {
        fetchers.add(fetcher);
    }

    @Override
    public void scrapForDay(Integer companyId, String from, String to, LocalDate date) {
        fetchers.stream()
                .filter(f -> f.getCompanyId() == companyId)
                .forEach(f-> {

                    List<Integer> routeIdsList = routeService.getRouteIdsByCitiesAndCompany(from, to, companyId);

                    for (int routeId: routeIdsList) {


                        Route route = routeRepository.getRouteByRouteId(routeId);

                        Integer fromDestId = route.getFromDestinationId();
                        Integer toDestId = route.getToDestinationId();

                        Destination fromDestination = destinationRepositoty.findOne(fromDestId);
                        Destination toDestination = destinationRepositoty.findOne(toDestId);
                        String fromRequestValue = fromDestination.getRequestValue();
                        String toRequestValue = toDestination.getRequestValue();

                        List<Price> listOfPrices = f.fetch(fromRequestValue, toRequestValue, date, routeId);
                        if (listOfPrices != null) {
                            for (Price p : listOfPrices) {
                                priceRepository.save(p);
                                System.out.println(p.getRouteId()+"  "+p.getPrice());
                            }
                        }
                    }
                });
    }

    @Override
    public void scrapForPeriod(Integer companyId, String from, String to, LocalDate date1, LocalDate date2) {

        LocalDate dateOfTrip = date1;

        while(dateOfTrip.isBefore(date2.plusDays(1))){
            scrapForDay(companyId, from, to, dateOfTrip);
            dateOfTrip = dateOfTrip.plusDays(1);
        }
    }

}
