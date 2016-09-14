package com.phototravel.services.impl.luxExpress;

import com.phototravel.entity.Company;
import com.phototravel.entity.Route;
import com.phototravel.services.CityService;
import com.phototravel.services.CompanyService;
import com.phototravel.services.DestinationService;
import com.phototravel.services.RouteService;
import com.phototravel.services.dbWriter.DBWriterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;

@Component
public class ScrapperImplM {

    static final Integer LUX_EXPRESS_ID = 2;
    static final Integer POLSKI_BUS_ID = 1;

    @Autowired
    ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    DBWriterService dbWriterService;

    @Autowired
    CompanyService companyService;

    @Autowired
    TaskFactory taskFactory;

    @PostConstruct
    public void init() {
        taskExecutor.setThreadNamePrefix("scannerThreadPool-");
    }


    public void scrap(Iterable<Route> routes, List<LocalDate> dates) {
        dbWriterService.startService();


        for (Route route : routes) {
            for (LocalDate date : dates) {
                addTaskToQueue(route, date);

            }

        }
        System.out.println();
        for (; ; ) {
            int count = taskExecutor.getActiveCount();

            System.out.println("Active Threads : " + count);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (count == 0) {
                // taskExecutor.shutdown();
                // dbWriterService.stopService();
                break;
            }
        }


    }

    private void addTaskToQueue(Route route, LocalDate date) {
        Company company = companyService.findCompanyById(route.getCompanyId());

        Task task = taskFactory.getTask(company.getCompanyName());
        task.setRoute(route);
        task.setDate(date);

        taskExecutor.execute(task);

    }

}
