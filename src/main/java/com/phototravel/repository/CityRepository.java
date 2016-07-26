package com.phototravel.repository;

import com.phototravel.entity.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by PBezdienezhnykh on 026 26.7.2016.
 */
@Repository
public interface CityRepository extends CrudRepository<City, Long> {


}
