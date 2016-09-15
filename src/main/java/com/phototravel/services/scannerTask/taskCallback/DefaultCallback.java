package com.phototravel.services.scannerTask.taskCallback;

import java.util.concurrent.CountDownLatch;

/**
 * Created by PBezdienezhnykh on 015 15.9.2016.
 */
public class DefaultCallback implements TaskCallback {

    CountDownLatch doneSignal;

    @Override
    public void setCountDownLatch(CountDownLatch countDownLatch) {
        this.doneSignal = countDownLatch;
    }

    @Override
    public void taskDone() {
        doneSignal.countDown();
        System.out.println("DefaultCallback taskDone" + doneSignal.getCount());
    }
}
