package com.phototravel.controllers;

import com.phototravel.services.oneTimeServices.PolskiBusCollector;
import com.phototravel.services.CacheManagerService;
import com.phototravel.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by PBezdienezhnykh on 021 21.7.2016.
 */
@Controller
public class SimpleController {

    @Autowired
    PolskiBusCollector polskiBusCollector;

    @Autowired
    CacheManagerService cacheManagerService;

    @Autowired
    CityService cityService;

    @RequestMapping("/hello")
    public ModelAndView helloWorld(@RequestParam(value="name", required=false, defaultValue="World") String name){
        System.out.println("helloWorld");
        ModelAndView modelAndView = new ModelAndView("hello");
        //    model.addAttribute("name", name);
        modelAndView.addObject("name", name);

        cacheManagerService.test();


        return modelAndView;
    }
}
