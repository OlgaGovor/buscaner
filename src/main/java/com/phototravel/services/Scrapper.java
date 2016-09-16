package com.phototravel.services;

import com.phototravel.controllers.entity.RequestForm;
import com.phototravel.entity.Company;
import com.phototravel.entity.Route;
import com.phototravel.services.threads.DBWriterService;
import com.phototravel.services.threads.ScannerMonitor;
import com.phototravel.services.scannerTask.BusScannerTask;
import com.phototravel.services.scannerTask.BusScannerTaskFactory;
import com.phototravel.services.scannerTask.taskCallback.DefaultCallback;
import com.phototravel.services.scannerTask.taskCallback.TaskCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Component
public class Scrapper {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    DBWriterService dbWriterService;

    @Autowired
    CompanyService companyService;

    @Autowired
    BusScannerTaskFactory taskFactory;

    @Autowired
    ScannerMonitor scannerMonitor;

    @Autowired
    RouteService routeService;

    @Autowired
    PriceService priceService;


    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @PostConstruct
    public void init() {
        taskExecutor.setThreadNamePrefix("scannerThreadPool-");
    }

    public void scrapForRequestForm(RequestForm requestForm) {

        List<Route> routes = routeService.findRoutesByCityIds(requestForm.getFromCity(), requestForm.getToCity(), false);

        List<LocalDate> dates = new ArrayList<>();
        if (!requestForm.isScanForPeriod()) {
            dates.add(LocalDate.parse(requestForm.getDepartureDate(), formatter));
        } else {
            LocalDate startDate = LocalDate.parse(requestForm.getDepartureDate(), formatter);
            LocalDate endDate = LocalDate.parse(requestForm.getDepartureDate(), formatter);
            while (startDate.isBefore(endDate.plusDays(1))) {
                dates.add(startDate);
                startDate = startDate.plusDays(1);
            }
        }


        CountDownLatch doneSignal = new CountDownLatch(dates.size() * routes.size());
        TaskCallback callback = new DefaultCallback();
        callback.setCountDownLatch(doneSignal);

        scrap(routes, dates, callback);

        try {
            doneSignal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void scrap(Iterable<Route> routes, List<LocalDate> dates) {
        scrap(routes, dates, null);
    }

    public void scrap(Iterable<Route> routes, List<LocalDate> dates, TaskCallback callback) {
        dbWriterService.startService();
        scannerMonitor.startMonitor();

        for (Route route : routes) {
            for (LocalDate date : dates) {
                logger.info("deleting old prices for " + route + date);
                priceService.movePriceToArchive(route.getRouteId(), Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()));

                addTaskToQueue(route, date, callback);
            }
        }

    }

    private void addTaskToQueue(Route route, LocalDate date, TaskCallback callback) {
        Company company = companyService.findCompanyById(route.getCompanyId());


        try {
            BusScannerTask task = taskFactory.getTask(company.getCompanyName());
            task.setRoute(route);
            task.setDate(date);
            task.setCallback(callback);
            task.setCompanyName(company.getCompanyName());

            taskExecutor.execute(task);

        } catch (NoSuchBeanDefinitionException e) {
            e.printStackTrace();
            callback.taskDone();
        }

    }

}
