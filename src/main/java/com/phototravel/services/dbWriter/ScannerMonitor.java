package com.phototravel.services.dbWriter;

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

    public void stopMonitor() {
        stop = true;
    }

    public void startMonitor() {
        stop = false;
        runMonitorThread();
    }

    public boolean isScanInProgress() {
        return stop;
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
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (count == 0) {
                        stopMonitor();
                        logger.info("Monitor stop");
                        break;
                    }

                }
            }

        };

        monitorThread = new Thread(monitor);
        monitorThread.setName("ScannerMonitor");
        monitorThread.start();
        logger.info("Monitor started");
    }


}
