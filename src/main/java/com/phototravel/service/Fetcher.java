package com.phototravel.service;

import com.phototravel.entity.Price;
import com.phototravel.model.FetcherType;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Olga_Govor on 8/16/2016.
 */
public interface Fetcher {
    List<Price> fetch(String fromRequestValue, String toRequestValue, LocalDate date, int routeId);

    FetcherType getType();
}
