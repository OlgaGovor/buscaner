package com.phototravel.services;

import com.phototravel.entity.Company;
import com.phototravel.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Olga_Govor on 7/26/2016.
 */
@Service
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;

    Company findCompanyById(Integer companyId) {
        for (Company company : companyRepository.findAll()) {
            if (company.getCompanyId() == companyId) {
                return company;
            }
        }
        return null;
    }
}
