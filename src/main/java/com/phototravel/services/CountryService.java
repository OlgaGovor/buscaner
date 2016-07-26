package com.phototravel.services;

import com.phototravel.entity.Country;
import com.phototravel.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Olga_Govor on 7/26/2016.
 */
@Service
public class CountryService {

    @Autowired
    CountryRepository countryRepository;

    public void createCountry(String countryName) {
        Country country = new Country(countryName);
        countryRepository.save(country);
    }
}
