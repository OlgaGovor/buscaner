package com.phototravel.services.scannerTask;

import com.phototravel.entity.Route;
import com.phototravel.services.scannerTask.taskCallback.TaskCallback;

import java.time.LocalDate;

/**
 * Created by comp on 14.09.2016.
 */
public interface BusScannerTask extends Runnable {

    void setRoute(Route route);

    void setDate(LocalDate date);

    void setCallback(TaskCallback callback);

    void setCompanyName(String companyName);


}
