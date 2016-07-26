package com.phototravel;

import com.phototravel.services.CityService;
import org.junit.Test;
import org.junit.runner.RunWith;
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

	@Test
	public void cityServiceTest(){
		CityService cityService = new CityService();
		cityService.createCity("Krakow",1);
	}

}
