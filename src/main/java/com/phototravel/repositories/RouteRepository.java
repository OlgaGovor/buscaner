package com.phototravel.repositories;

import com.phototravel.entity.Route;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by PBezdienezhnykh on 026 26.7.2016.
 */
@Repository
public interface RouteRepository extends CrudRepository<Route, Integer> {


    @Override
    @Cacheable("route")
    Iterable<Route> findAll();

    @Override
    @CacheEvict("route")
    Route save(Route s);

   /* @Query(value = "select route_id from route r " +
            " where r.from_city_id= :fromCityId and r.to_city_id= :toCityId"
            , nativeQuery = true
    )
    List<Integer> getRouteIdByCityId(@Param("fromCityId") Integer fromCityId,
                                     @Param("toCityId") Integer toCityId);*/

 /*   @Query(value = "select route_id from route r " +
            " where r.from_city_id= :fromCityId and r.to_city_id= :toCityId and r.company_id= :companyId" +
            " and is_active = TRUE "
            , nativeQuery = true
    )
    List<Integer> getRouteIdByCityIdAndCompanyId(@Param("fromCityId") Integer fromCityId,
                                                 @Param("toCityId") Integer toCityId,
                                                 @Param("companyId") Integer companyId);

  */

}
