package com.phototravel.services.scannerTask.taskCallback;

import java.util.concurrent.CountDownLatch;

/**
 * Created by PBezdienezhnykh on 015 15.9.2016.
 */
public interface TaskCallback {
    void taskDone();

    void setCountDownLatch(CountDownLatch countDownLatch);
}
