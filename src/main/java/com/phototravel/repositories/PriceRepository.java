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


   /* @Transactional
    @Modifying
    @Query(value = "insert into price_archive "+
            "select route_id,\n" +
            "      departure_date,\n" +
            "      departure_time,\n" +
            "      price,\n" +
            "      last_update,\n" +
            "      arrival_time,\n" +
            "      currency " +

            "from price " +
            "where route_id=:routeId " +
            "and departure_date=date(:departureDate);"
            , nativeQuery = true
    )
    void movePriceToArchive(@Param("routeId") int routId, @Param("departureDate") Date departureDate);

    @Transactional
    void deleteAllByIdRouteIdAndIdDepartureDate(int routId, Date departureDate);*/
}
