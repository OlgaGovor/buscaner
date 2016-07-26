package com.phototravel.repository;

import com.phototravel.entity.Price;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created by PBezdienezhnykh on 026 26.7.2016.
 */
@Repository
public interface PriceRepository extends CrudRepository<Price, Long> {

    @Query(value = "select min(price)\n" +
            "from destination df\n" +
            "  join destination dt on dt.company_id = df.company_id\n" +
            "  join route r on r.from_destination_id = df.destination_id\n" +
            "    and r.to_destination_id = dt.destination_id\n" +
            "    and r.company_id = dt.company_id\n" +
            "  join price p on p.route_id = r.route_id\n" +

            " where df.city_id= :fromCity " +
            " and dt.city_id= :toCity " +
            " and p.departure_date = :departureDate"
            , nativeQuery = true
    )
    double findChipestBusByRequestForm(@Param("fromCity") int fromCityId,
                                       @Param("toCity") int toCityId,
                                       @Param("departureDate") Date departureDate);
}
