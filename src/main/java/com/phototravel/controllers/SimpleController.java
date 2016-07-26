package com.phototravel.controllers;

import com.phototravel.dao.TestDao;
import com.phototravel.dataCollectors.PolskiBusCollector;
import com.phototravel.dataCollectors.Route;
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
    TestDao testDao;

    @Autowired
    CityService cityService;

    @RequestMapping("/hello")
    public ModelAndView helloWorld(@RequestParam(value="name", required=false, defaultValue="World") String name){
        System.out.println("helloWorld");
        ModelAndView modelAndView = new ModelAndView("hello");
        //    model.addAttribute("name", name);
        modelAndView.addObject("name", name);

        cityService.createCity(name, 1);
        try {
            Route r = new Route();
            //  polskiBusCollector.getPriceForDate(r);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //List<Route> routes = testDao.readAllRoutes();

        //modelAndView.addObject("routes", routes);

        return modelAndView;
    }
}
