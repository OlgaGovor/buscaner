package com.phototravel.controllers;

import com.phototravel.controllers.entity.RequestForm;
import com.phototravel.entity.City;
import com.phototravel.entity.ResultDetails;
import com.phototravel.repositories.PriceRepository;
import com.phototravel.services.CityService;
import com.phototravel.services.FindBusService;
import com.phototravel.services.Scrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * Created by PBezdienezhnykh on 026 26.7.2016.
 */
@Controller
@RequestMapping(value = {"/", "/index.html", "/index", "/updateData"})
public class IndexController {

    @Autowired
    CityService cityService;

    @Autowired
    PriceRepository priceRepository;

    @Autowired
    FindBusService findBusService;

    @Autowired
    Scrapper scrapper;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/updateData", method = RequestMethod.POST)
    public String updateData(Model model, @ModelAttribute RequestForm requestForm) {
        logger.info("updateData " + requestForm.toString());

        scrapper.scrapForRequestForm(requestForm);
        List<ResultDetails> resultDetailsList = findBusService.findBus(requestForm);
        model.addAttribute("resultDetailsList", resultDetailsList);
        return "resultTable :: resultTable";
    }

    @RequestMapping()
    public ModelAndView defaultRequest() {
        logger.info("defaultRequest");
        ModelAndView modelAndView = new ModelAndView("index");

        Iterable<City> cities = cityService.findAll();
        modelAndView.addObject("cities", cities);

        modelAndView.addObject("requestForm", new RequestForm());

        return modelAndView;
    }

    @RequestMapping(value = {"/", "/index.html", "/index"}, method = RequestMethod.POST)
    public ModelAndView formRequest(@ModelAttribute RequestForm requestForm) {

        logger.info("formRequest " + requestForm.toString());

        ModelAndView modelAndView = new ModelAndView();

        //add cities list on the page
        Iterable<City> cities = cityService.findAll();
        modelAndView.addObject("cities", cities);

        if (!validateRequestForm(requestForm)) {
            logger.info("Invalid RequestForm");
            modelAndView.setViewName("index");
            modelAndView.addObject("message", "Invalid RequestForm");
            return modelAndView;
        }


        List<ResultDetails> resultDetailsList = findBusService.findBus(requestForm);

        if (resultDetailsList != null) {
            modelAndView.addObject("resultDetailsList", resultDetailsList);
        }

        if (!requestForm.isScanForPeriod()) {
            modelAndView.setViewName("index");


        }
        else {
            modelAndView.setViewName("priceRangeView");

            Date date1 = requestForm.getDepartureAsDate();
            Date date2 = requestForm.getDepartureEndAsDate();
            LocalDate d1 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate d2 = date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();


            modelAndView.addObject("startYear", d1.getYear());
            modelAndView.addObject("startMonth", d1.getMonthValue()-1);
            modelAndView.addObject("startDay", d1.getDayOfMonth());

            modelAndView.addObject("endYear", d2.getYear());
            modelAndView.addObject("endMonth", d2.getMonthValue()-1);
            modelAndView.addObject("endDay", d2.getDayOfMonth());
        }

        return modelAndView;
    }


//    @RequestMapping(value = {"/route/{from}/{to}"})
//    public ModelAndView findRoute(@PathVariable("from") Integer fromCityId,
//                                  @PathVariable("to") Integer toCityId,
//                                  @RequestParam("startDate") String fromDate,
//                                  @RequestParam("toDate") String toDate,
//                                  @RequestParam(value = "format", required = false) String format) {
//        System.out.println(fromCityId + " " + toCityId + " " + fromDate + " " + toDate + " " + format);
//        ModelAndView modelAndView = new ModelAndView("calendarView");
//        CalendarView calendarView = new CalendarView(fromDate, toDate);
//
//        modelAndView.addObject("calendarView", calendarView);
//
//        return modelAndView;
//    }


    private boolean validateRequestForm(RequestForm requestForm) {
        if (requestForm.getFromCity() < 0)
            return false;
        if (requestForm.getToCity() < 0)
            return false;

        if (requestForm.getDepartureDate() == null || requestForm.getDepartureDate().isEmpty())
            return false;
        if (requestForm.isScanForPeriod() && (requestForm.getDepartureDateEnd() == null || requestForm.getDepartureDateEnd().isEmpty()))
            return false;

        return true;
    }
}
