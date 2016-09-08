package com.phototravel.controllers;

import com.phototravel.controllers.entity.RequestForm;
import com.phototravel.controllers.entity.ResultDetails;
import com.phototravel.entity.City;
import com.phototravel.repositories.PriceRepository;
import com.phototravel.services.CityService;
import com.phototravel.services.FindBusService;
import com.phototravel.services.RouteService;
import com.phototravel.services.Scrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * Created by PBezdienezhnykh on 026 26.7.2016.
 */
@Controller
@RequestMapping(value = {"/", "/index.html", "/index", "/updateData", "/searchData", "/loadRoutes"})
public class IndexController {

    @Autowired
    CityService cityService;

    @Autowired
    RouteService routeService;

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
        return "resultTable :: resultList";
    }

    @RequestMapping(value = "/loadRoutes", method = RequestMethod.POST)
    @ResponseBody
    public List<City> loadRoutes(@RequestParam("cityId") Integer cityId, @RequestParam("depDst") String depDst) {
        logger.info("loadRoutes " + cityId);

        return routeService.findRouteCities(cityId, depDst);
    }

    @RequestMapping(value = "/searchData", method = RequestMethod.POST)
    public String searchData(Model model, @ModelAttribute RequestForm requestForm) {
        logger.info("searchData " + requestForm.toString());

        String errorMessage = validateRequestForm(requestForm);
        if (errorMessage != null) {
            logger.info(errorMessage);
            model.addAttribute("resultMessage", errorMessage);
        } else {
            List<ResultDetails> resultDetailsList = findBusService.findBus(requestForm);
            model.addAttribute("resultDetailsList", resultDetailsList);
        }

        if (requestForm.isScanForPeriod()) {

            Date date1 = requestForm.getDepartureAsDate();
            Date date2 = requestForm.getDepartureEndAsDate();
            LocalDate d1 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate d2 = date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();


            model.addAttribute("startYear", d1.getYear());
            model.addAttribute("startMonth", d1.getMonthValue() - 1);
            model.addAttribute("startDay", d1.getDayOfMonth());

            model.addAttribute("endYear", d2.getYear());
            model.addAttribute("endMonth", d2.getMonthValue() - 1);
            model.addAttribute("endDay", d2.getDayOfMonth());
        }


        String viewType = "";
        if (requestForm.isScanForPeriod()) {
            viewType += "calendarView :: resultCalendar";
        } else {
            viewType += "resultTable :: resultList";
        }


        return viewType;
    }

    @RequestMapping()
    public ModelAndView defaultRequest() {
        logger.info("defaultRequest");
        ModelAndView modelAndView = new ModelAndView("index");
        try {


            Iterable<City> cities = cityService.findAll();
            modelAndView.addObject("cities", cities);
        }
        catch (Exception e){
            modelAndView.addObject("resultMessage", "Error:" + e.getMessage());
        }
        modelAndView.addObject("requestForm", new RequestForm());

        return modelAndView;
    }

  /*  @RequestMapping(value = {"/", "/index.html", "/index"}, method = RequestMethod.POST)
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

*/
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


    private String validateRequestForm(RequestForm requestForm) {
        String defaultErrorMessage = "Invalid request form!";
        if (requestForm.getFromCity() < 0)
            return defaultErrorMessage;
        if (requestForm.getToCity() < 0)
            return defaultErrorMessage;

        if (requestForm.getDepartureDate() == null || requestForm.getDepartureDate().isEmpty())
            return defaultErrorMessage;
        if (requestForm.isScanForPeriod() && (requestForm.getDepartureDateEnd() == null || requestForm.getDepartureDateEnd().isEmpty()))
            return defaultErrorMessage;

        if (!findBusService.checkIfRouteExists(requestForm.getFromCity(), requestForm.getToCity(), false)) {
            return "No such route Exists!";
        }

        return null;
    }
}
