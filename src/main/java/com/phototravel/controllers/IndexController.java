package com.phototravel.controllers;

import com.phototravel.controllers.entity.RequestForm;
import com.phototravel.entity.City;
import com.phototravel.repository.CityRepository;
import com.phototravel.services.FindBusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by PBezdienezhnykh on 026 26.7.2016.
 */
@Controller
@RequestMapping(value = {"/", "/index.html", "/index"})
public class IndexController {

    @Autowired
    CityRepository cityRepository;

    @Autowired
    FindBusService findBusService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView defaultRequest() {
        System.out.println("defaultRequest");
        ModelAndView modelAndView = new ModelAndView("index");

        Iterable<City> cities = cityRepository.findAll();
        modelAndView.addObject("cities", cities);

        modelAndView.addObject("requestForm", new RequestForm());

        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView formRequest(@ModelAttribute RequestForm requestForm) {

        System.out.println(requestForm.getFromCity() + " " + requestForm.getToCity());

        ModelAndView modelAndView = new ModelAndView("index");
        Iterable<City> cities = cityRepository.findAll();
        modelAndView.addObject("cities", cities);


        Double price = findBusService.findBus(requestForm);
        System.out.println("price=" + price);
        if (price != null) {
            modelAndView.addObject("minPrice", price);
        }


        return modelAndView;
    }

}
