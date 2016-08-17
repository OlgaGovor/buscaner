package com.phototravel.repository;

import com.phototravel.entity.Country;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Olga_Govor on 7/26/2016.
 */
@Repository
public interface CountryRepository extends CrudRepository<Country, Integer> {

    @Query(value = "select country_name from country c where c.country_id= :countryId"
            , nativeQuery = true
    )
    String findCountryById(@Param("countryId") int countryId);
}
