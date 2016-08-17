package com.phototravel.configuration;

import com.phototravel.service.Scrapper;
import com.phototravel.service.impl.LuxexpressFetcher;
import com.phototravel.service.impl.PolskiBusFetcher;
import com.phototravel.service.impl.ScrapperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Olga_Govor on 8/16/2016.
 */
@Configuration
public class BuscanerConfiguration {

    @Bean
    public Scrapper scrapper() {
        Scrapper scrapper = new ScrapperImpl();
        scrapper.register(new PolskiBusFetcher());
        scrapper.register(new LuxexpressFetcher());
        //register all fetchers
        return scrapper;
    }

}
