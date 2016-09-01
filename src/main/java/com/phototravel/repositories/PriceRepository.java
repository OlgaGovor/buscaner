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


  /*  @Query(value =
            "SELECT NEW com.phototravel.controllers.entity.PriceCalendar (" +
            "  p.id.departureDate, " +
            "  min(p.price) AS price ) " +
            "FROM Price p " +
            "  JOIN Route r on r.routeId = p.id.routeId " +
            "WHERE r.fromCityId = :fromCity " +
            "      AND r.toCityId = :toCity " +
            "      AND p.id.departureDate >= date(:departureDate) " +
            "      AND p.id.departureDate <= date(:departureDateEnd) " +
            "GROUP BY p.id.departureDate " +
            "ORDER BY p.id.departureDate ASC"

    )
    List<PriceCalendar> findCheapestBusByRequestForm(@Param("fromCity") int fromCityId,
                                           @Param("toCity") int toCityId,
                                           @Param("departureDate") Date departureDate,
                                           @Param("departureDateEnd") Date departureDateEnd);

*/
}
