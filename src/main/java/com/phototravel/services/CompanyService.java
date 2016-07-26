package com.phototravel.services;

import com.phototravel.entity.Company;
import com.phototravel.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Olga_Govor on 7/26/2016.
 */
@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    public void createCompany(String name, String companyUrl) {
        Company company = new Company(name, companyUrl);
        companyRepository.save(company);
    }
}
