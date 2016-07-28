package com.phototravel.repository;

import com.phototravel.entity.Company;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by Olga_Govor on 7/26/2016.
 */
public interface CompanyRepository extends CrudRepository<Company, Long> {

    @Override
    @Cacheable("company")
    Iterable<Company> findAll();

    @Query(value = "select company_id from company c where c.company_name= :companyName"
            , nativeQuery = true
    )
    Integer findCompanyByName(@Param("companyName") String companyName);
}