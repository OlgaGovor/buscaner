package com.phototravel.repository;

import com.phototravel.entity.City;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by PBezdienezhnykh on 026 26.7.2016.
 */
@Repository
public interface CityRepository extends CrudRepository<City, Long> {
    @Query(value = "select city_id from city c where c.city_name= :cityName"
            , nativeQuery = true
    )
    Integer findCityByName(@Param("cityName") String cityName);

    @Override
    @Cacheable("city")
    Iterable<City> findAll();

    @Override
    @CacheEvict("city")
    City save(City s);
}
