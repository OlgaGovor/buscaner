package com.phototravel.services.oneTimeServices;

/**
 * Created by Olga_Govor on 8/18/2016.
 */
public interface CitiesAndRoutesFetcher {

    void fetchCities();

    void fetchRoutes(Integer companyId);

    void fetchDestinations(Integer companyId);
}
