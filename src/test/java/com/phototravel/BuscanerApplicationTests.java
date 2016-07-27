package com.phototravel;

import com.phototravel.repository.PriceRepository;
import com.phototravel.services.CityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

	@Test
	public void ееTest() throws ParseException {
		int fromCity = 1;
		int toCity = 2;

		String d = "25/01/2015";

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date = formatter.parse(d);

		Double a = priceRepository.findChipestBusByRequestForm(fromCity, toCity, date);
		System.out.println("Price: "+a);
	}

}
