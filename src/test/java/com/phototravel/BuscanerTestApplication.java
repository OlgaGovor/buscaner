package com.phototravel;

import com.phototravel.services.Scrapper;
import com.phototravel.services.impl.LuxexpressFetcher;
import com.phototravel.services.impl.PolskiBusFetcher;
import com.phototravel.services.impl.ScrapperImpl;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

/**
 * Created by PBezdienezhnykh on 025 25.8.2016.
 */
@SpringBootApplication
public class BuscanerTestApplication {

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("classpath:testSchema.sql")
                .addScript("classpath:testData.sql")
                .build();
    }

    @Bean
    public Scrapper scrapper() {
        Scrapper scrapper = new ScrapperImpl();
        scrapper.register(new PolskiBusFetcher());
        scrapper.register(new LuxexpressFetcher());
        //register all fetchers
        return scrapper;
    }
}
