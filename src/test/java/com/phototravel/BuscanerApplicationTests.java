package com.phototravel;

import com.phototravel.services.CountryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BuscanerApplication.class)
@WebAppConfiguration
public class BuscanerApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Autowired
	CountryService countryService;

	@Test
	public void addCountries(){
//		countryService.createCountry("Poland");
//		countryService.createCountry("Germany");
//		countryService.createCountry("Czech Republic");
//		countryService.createCountry("Austria");
//		countryService.createCountry("Hungary");
//		countryService.createCountry("Ukraine");
//		countryService.createCountry("Slovakia");
	}

}
