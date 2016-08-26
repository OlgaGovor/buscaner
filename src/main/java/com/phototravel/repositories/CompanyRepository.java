package com.phototravel.repositories;

import com.phototravel.entity.Company;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Olga_Govor on 7/26/2016.
 */
@Repository
public interface CompanyRepository extends CrudRepository<Company, Integer> {

    @Override
    @Cacheable("company")
    Iterable<Company> findAll();

    @Query(value = "select company_id from company c where c.company_name= :companyName"
            , nativeQuery = true
    )
    Integer findCompanyByName(@Param("companyName") String companyName);

    @Query(value = "select company_name from company c where c.company_id= :companyId"
            , nativeQuery = true
    )
    String findCompanyById(@Param("companyId") Integer companyId);
}
