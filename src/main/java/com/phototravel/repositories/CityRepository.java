package com.phototravel.repositories;

import com.phototravel.entity.City;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by PBezdienezhnykh on 026 26.7.2016.
 */
@Repository
public interface CityRepository extends CrudRepository<City, Integer> {

    @Override
    @CacheEvict("city")
    City save(City s);

    @Override
    @Cacheable("city")
    List<City> findAll();
}
