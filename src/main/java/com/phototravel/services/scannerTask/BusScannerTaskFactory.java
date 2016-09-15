package com.phototravel.services.scannerTask;

/**
 * Created by comp on 14.09.2016.
 */
public interface BusScannerTaskFactory {

    BusScannerTask getTask(String companyName);
}
