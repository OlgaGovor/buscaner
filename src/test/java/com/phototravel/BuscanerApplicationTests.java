package com.phototravel;

import com.phototravel.entity.City;
import com.phototravel.entity.Country;
import com.phototravel.repositories.CityRepository;
import com.phototravel.repositories.CountryRepository;
import com.phototravel.services.CountryService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BuscanerTestApplication.class)
@WebAppConfiguration
public class BuscanerApplicationTests {

	@Test
	@Ignore
	public void contextLoads() {
	}

	@Autowired
	CountryService countryService;

	@Autowired
	CityRepository cityRepository;

	@Autowired
	CountryRepository countryRepository;

	@Test
	@Ignore
	public void addCountries(){
//		countryService.createCountry("Poland");
//		countryService.createCountry("Germany");
//		countryService.createCountry("Czech Republic");
//		countryService.createCountry("Austria");
//		countryService.createCountry("Hungary");
//		countryService.createCountry("Ukraine");
//		countryService.createCountry("Slovakia");
	}


	@Test
	public void testDBConn() {
		Country testCountry = countryRepository.save(new Country("testCountry"));
		System.out.println("saved=" + testCountry.getCountryId() + " " + testCountry.getCountryName());
		cityRepository.save(new City("testCity", testCountry.getCountryId()));
		System.out.println("save new city");
		Iterable<City> cities = cityRepository.findAll();
		for (City city : cities) {
			System.out.println(city.getCityId() + " " + city.getCityName());
		}

		System.out.println("end");
	}

}
