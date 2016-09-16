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


    @Cacheable("route2")
    @Query(value = "select * from route r " +
            " where r.from_city_id= :fromCityId and r.to_city_id= :toCityId and r.company_id= :companyId" +
            " and is_active = TRUE and has_changes = :hasChanges"
            , nativeQuery = true
    )
    List<Route> getRoutesByCityIdAndCompanyId(@Param("fromCityId") Integer fromCityId,
                                                 @Param("toCityId") Integer toCityId,
                                                 @Param("companyId") Integer companyId,
                                              @Param("hasChanges") Boolean hasChanges);

    @Cacheable("route2")
    @Query(value = "select route_id from route r " +
            " where r.from_city_id= :fromCityId and r.to_city_id= :toCityId " +
            " and is_active = TRUE and has_changes = :hasChanges"
            , nativeQuery = true
    )
    List<Integer> findByCityIds(@Param("fromCityId") Integer fromCityId,
                                @Param("toCityId") Integer toCityId,
                                @Param("hasChanges") Boolean hasChanges);

    @Cacheable("route2")
    @Query(value = "select * from route r " +
            " where r.from_city_id= :fromCityId and r.to_city_id= :toCityId " +
            " and is_active = TRUE and has_changes = :hasChanges"
            , nativeQuery = true
    )
    List<Route> findRoutesByCityIds(@Param("fromCityId") Integer fromCityId,
                                    @Param("toCityId") Integer toCityId,
                                    @Param("hasChanges") Boolean hasChanges);


    @Cacheable("route3")
    @Query(value = "select distinct to_city_id from route r " +
            " where r.from_city_id= :fromCityId " +
            " and is_active = TRUE and has_changes = FALSE "
            , nativeQuery = true
    )
    List<Integer> findByFromCityId(@Param("fromCityId") Integer fromCityId);

    @Cacheable("route3")
    @Query(value = "select distinct from_city_id from route r " +
            " where r.to_city_id= :toCityId " +
            " and is_active = TRUE and has_changes = FALSE "
            , nativeQuery = true
    )
    List<Integer> findByToCityId(@Param("toCityId") Integer toCityId);

    @Query(value = "from Route " +
            " where hasChanges = :hasChanges "
    )
    List<Route> findAll(@Param("hasChanges") Boolean hasChanges);
}
