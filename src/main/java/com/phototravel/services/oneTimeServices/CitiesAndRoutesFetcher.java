package com.phototravel.services.oneTimeServices;

import com.phototravel.modelOfFetcher.FetcherType;

/**
 * Created by Olga_Govor on 8/18/2016.
 */
public interface CitiesAndRoutesFetcher {

    void fetchCities(FetcherType fetcherType);
    void fetchRoutes(FetcherType fetcherType);
    void fetchDestinations(FetcherType fetcherType);
}
