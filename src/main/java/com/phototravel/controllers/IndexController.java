package com.phototravel.controllers;

import com.phototravel.controllers.entity.RequestForm;
import com.phototravel.entity.City;
import com.phototravel.entity.Price;
import com.phototravel.repository.PriceRepository;
import com.phototravel.services.CityService;
import com.phototravel.services.FindBusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

/**
 * Created by PBezdienezhnykh on 026 26.7.2016.
 */
@Controller
@RequestMapping(value = {"/", "/index.html", "/index"})
public class IndexController {

    @Autowired
    CityService cityService;

    @Autowired
    PriceRepository priceRepository;

    @Autowired
    FindBusService findBusService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView defaultRequest() {
        System.out.println("defaultRequest");
        ModelAndView modelAndView = new ModelAndView("index");

        Iterable<City> cities = cityService.findAll();
        modelAndView.addObject("cities", cities);

        modelAndView.addObject("requestForm", new RequestForm());

        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView formRequest(@ModelAttribute RequestForm requestForm) {

        System.out.println(requestForm.getFromCity() + " " + requestForm.getToCity());

        ModelAndView modelAndView = new ModelAndView("index");
        System.out.println("cities1=" + new Date().getTime());
        Iterable<City> cities = cityService.findAll();
        System.out.println("cities2=" + new Date().getTime());

        City one = cityService.findOne(3);
        System.out.println("cities3=" + new Date().getTime());
        modelAndView.addObject("cities", cities);


        List<Price> prices = findBusService.findBus(requestForm);

        if (prices != null) {
            modelAndView.addObject("prices", prices);
        }


        return modelAndView;
    }

}
