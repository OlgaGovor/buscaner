package com.phototravel.repositories;

import com.phototravel.entity.Price;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by PBezdienezhnykh on 026 26.7.2016.
 */
@Repository
public interface PriceRepository extends CrudRepository<Price, Integer> {

    @Query(value = "select p.* \n" +
            " from route r " +
            " join price p on p.route_id = r.route_id\n" +
            " where r.from_city_id= :fromCity " +
            " and r.to_city_id= :toCity " +
            " and p.departure_date >=date(:departureDate) " +
            " and p.departure_date <=date(:departureDateEnd)"
            , nativeQuery = true
    )
    List<Price> findBusByRequestForm(@Param("fromCity") int fromCityId,
                                     @Param("toCity") int toCityId,
                                     @Param("departureDate") Date departureDate,
                                     @Param("departureDateEnd") Date departureDateEnd);



    @Query(value = "select route_id, departure_date, departure_time, min(p.price)as price, last_update, arrival_time, currency" +
            " from price p " +
            " group by p.departure_date, route_id"+
            " having p.route_id in (" +
            " select route_id from route r where"+
            " r.from_city_id= :fromCity " +
            " and r.to_city_id= :toCity)" +
            " and p.departure_date >=date(:departureDate) " +
            " and p.departure_date <=date(:departureDateEnd)"+
            " order by departure_date ASC"
            , nativeQuery = true
    )
    List<Price> findCheapestBusByRequestForm(@Param("fromCity") int fromCityId,
                                                     @Param("toCity") int toCityId,
                                                     @Param("departureDate") Date departureDate,
                                                     @Param("departureDateEnd") Date departureDateEnd);


}
