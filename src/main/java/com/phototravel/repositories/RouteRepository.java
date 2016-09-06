package com.phototravel.repositories;

import com.phototravel.entity.Route;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by PBezdienezhnykh on 026 26.7.2016.
 */
@Repository
public interface RouteRepository extends CrudRepository<Route, Integer> {


    @Cacheable("route")
    Route findRouteByRouteId(int routeId);

    @Override
    @CacheEvict(cacheNames = {"route", "route2"}, allEntries = true)
    Route save(Route s);

   /* @Query(value = "select route_id from route r " +
            " where r.from_city_id= :fromCityId and r.to_city_id= :toCityId"
            , nativeQuery = true
    )
    List<Integer> getRouteIdByCityId(@Param("fromCityId") Integer fromCityId,
                                     @Param("toCityId") Integer toCityId);*/

    @CacheEvict("route2")
    @Query(value = "select * from route r " +
            " where r.from_city_id= :fromCityId and r.to_city_id= :toCityId and r.company_id= :companyId" +
            " and is_active = TRUE "
            , nativeQuery = true
    )
    List<Route> getRoutesByCityIdAndCompanyId(@Param("fromCityId") Integer fromCityId,
                                                 @Param("toCityId") Integer toCityId,
                                                 @Param("companyId") Integer companyId);


}
