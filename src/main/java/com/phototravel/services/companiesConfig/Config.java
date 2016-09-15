package com.phototravel.services.companiesConfig;

import com.phototravel.entity.Route;

/**
 * Created by PBezdienezhnykh on 015 15.9.2016.
 */
public interface Config {

    String getParam(String paramName);

    String getLink(Route route, String date);
}
