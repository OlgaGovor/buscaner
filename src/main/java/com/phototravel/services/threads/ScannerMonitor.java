package com.phototravel.services.threads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class ScannerMonitor {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DBWriterService dbWriterService;

    @Autowired
    ThreadPoolTaskExecutor taskExecutor;


    private Thread monitorThread;
    private boolean stop;
    Object monitorObj = new Object();

    public void stopMonitor() {
        stop = true;
    }

    public synchronized void startMonitor() {

        if (monitorThread == null || !monitorThread.isAlive()) {
            stop = false;
            runMonitorThread();
        } else {
            resumeMonitorThread();
        }

    }

    public boolean isScanInProgress() {
        return !stop;
    }

    public void pauseMonitorThread() {

    }

    public void resumeMonitorThread() {

        synchronized (monitorObj) {
            monitorObj.notify();
        }
    }

    private void runMonitorThread() {
        Runnable monitor = new Runnable() {
            public void run() {
                while (!stop) {



                    int count = taskExecutor.getActiveCount();


                    logger.info("Active Threads : " + count +
                            " Total BusScannerTask count: " + taskExecutor.getThreadPoolExecutor().getTaskCount() +
                            " Done BusScannerTask count: " + taskExecutor.getThreadPoolExecutor().getCompletedTaskCount() +
                            " Queue size: " + taskExecutor.getThreadPoolExecutor().getQueue().size());
                    logger.info(dbWriterService.getStatistics());

                    if (count == 0 && !dbWriterService.isStarted()) {
                        synchronized (monitorObj) {
                            try {
                                logger.info("Monitor sleep until events");
                                monitorObj.wait();
                                logger.info("Monitor awaken");
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        try {
                            logger.info("Monitor sleep for 500ms");
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
                stop = false;
            }

        };

        monitorThread = new Thread(monitor);
        monitorThread.setName("ScannerMonitor");
        monitorThread.start();
        logger.info("Monitor started");
    }


}
