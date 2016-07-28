package com.phototravel.repository;

import com.phototravel.entity.Country;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Olga_Govor on 7/26/2016.
 */
public interface CountryRepository extends CrudRepository<Country, Long> {
}
