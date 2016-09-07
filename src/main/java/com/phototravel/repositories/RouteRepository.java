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
    @CacheEvict(cacheNames = {"route", "route2", "route3"}, allEntries = true)
    Route save(Route s);


    @CacheEvict("route2")
    @Query(value = "select * from route r " +
            " where r.from_city_id= :fromCityId and r.to_city_id= :toCityId and r.company_id= :companyId" +
            " and is_active = TRUE and has_changes = :hasChanges"
            , nativeQuery = true
    )
    List<Route> getRoutesByCityIdAndCompanyId(@Param("fromCityId") Integer fromCityId,
                                                 @Param("toCityId") Integer toCityId,
                                                 @Param("companyId") Integer companyId,
                                                 @Param("hasChanges") Integer hasChanges);


    @Cacheable("route3")
    List<Route> findByFromCityId(Integer fromCityId);

    @Cacheable("route3")
    List<Route> findByToCityId(Integer fromCityId);
}
