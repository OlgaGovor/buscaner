package com.phototravel.services;

import com.phototravel.controllers.entity.RequestForm;
import com.phototravel.entity.Route;

import java.time.LocalDate;

/**
 * Created by Olga_Govor on 8/16/2016.
 */
public interface Scrapper {
    void register(Integer companyId, Fetcher fetcher);

    void scrapForDay(Integer companyId, String from, String to, LocalDate date);

    void scrapForDay(Integer companyId, int from, int to, LocalDate date);

    void scrapForPeriod(Integer companyId, String from, String to, LocalDate date1, LocalDate date2);

    void scrapForPeriod(Integer companyId, int fromId, int toId, LocalDate date1, LocalDate date2);

    void scrapRouteForPeriod(Route route, LocalDate startDate, LocalDate endDate);
    void scrapRouteForDate(Route route,  LocalDate date);

    void scrapForRequestForm(RequestForm requestForm);

    void scrapAllForDay(int fromId, int toId, LocalDate date);

    void scrapAllForDay(String from, String to, LocalDate date);

    void scrapAllForPeriod(int fromId, int toId, LocalDate date1, LocalDate date2);

    void scrapAllForPeriod(String from, String to, LocalDate date1, LocalDate date2);

    String getLink(Route route, String date);
}
