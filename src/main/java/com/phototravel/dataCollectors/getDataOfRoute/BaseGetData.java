package com.phototravel.dataCollectors.getDataOfRoute;

import com.phototravel.dataCollectors.Route;

/**
 * Created by Olga_Govor on 7/20/2016.
 */
public interface BaseGetData {

    //route with base information and 'to', 'from' adopted for current company
    Route getData(Route route, String to, String from) throws Exception;

}
