package com.phototravel;

import com.phototravel.entity.Price;
import com.phototravel.repository.PriceRepository;
import com.phototravel.services.CityService;
import com.phototravel.services.CountryService;
import com.phototravel.services.PriceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BuscanerApplication.class)
@WebAppConfiguration
public class BuscanerApplicationTests {

	@Autowired
	CityService cityService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void cityServiceTest(){
		cityService.createCity("Krakow",1);
	}

	@Autowired
	PriceRepository priceRepository;

	@Autowired
	PriceService priceService;

	@Test
	public void ееTest() throws ParseException {
		String d = "25/01/2015";

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date = formatter.parse(d);
		long t =0;
		Time time = new Time(t);
		Double price = 15.99;

		Date now = new Date();

		priceService.createPrice(1,date, time, price, now);

		int fromCity = 3;
		int toCity = 4;


		List<Price> prices = priceRepository.findChipestBusByRequestForm(fromCity, toCity, date, date);
		System.out.println("Price: "+prices.size());
	}

	@Autowired
	CountryService countryService;

	@Test
	public void addCountries(){
//		countryService.createCountry("Poland");
		countryService.createCountry("Germany");
		countryService.createCountry("Czech Republic");
		countryService.createCountry("Austria");
		countryService.createCountry("Hungary");
		countryService.createCountry("Ukraine");
		countryService.createCountry("Slovakia");
	}

}
