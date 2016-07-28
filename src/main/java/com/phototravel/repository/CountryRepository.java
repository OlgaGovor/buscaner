package com.phototravel.repository;

import com.phototravel.entity.Country;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by Olga_Govor on 7/26/2016.
 */
public interface CountryRepository extends CrudRepository<Country, Long> {

    @Query(value = "select country_name from country c where c.country_id= :countryId"
            , nativeQuery = true
    )
    String findCountryById(@Param("countryId") int countryId);
}
