package com.phototravel.services;

import java.time.LocalDate;

/**
 * Created by Olga_Govor on 8/16/2016.
 */
public interface Scrapper {
    void register(Fetcher fetcher);

    void scrapForDay(Integer companyId, String from, String to, LocalDate date);

    void scrapForPeriod(Integer companyId, String from, String to, LocalDate date1, LocalDate date2);

}
