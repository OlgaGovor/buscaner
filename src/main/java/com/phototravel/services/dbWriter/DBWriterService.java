package com.phototravel.services.dbWriter;

import com.phototravel.entity.Price;
import com.phototravel.services.PriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;

@Component
public class DBWriterService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    ArrayBlockingQueue<Price> priceQueue = new ArrayBlockingQueue<Price>(100);
    private volatile boolean stop;
    private Thread writerThread;
    private Object monitor = new Object();

    @Autowired
    PriceService priceService;


    public void addToQueue(Price price) throws InterruptedException {
        logger.info("addToQueue");
        priceQueue.put(price);

        synchronized (monitor) {
            monitor.notifyAll();
        }
    }

    public Price take() throws InterruptedException {


        if (priceQueue.isEmpty()) {
            return null;
        }

        logger.info("take queue size=" + priceQueue.size());
        return priceQueue.take();
    }

    public void stopService() {
        stop = true;
        logger.info("stop");
    }

    public boolean isStarted() {
        return !stop;
    }


    public void startService() {
        Runnable writer = new Runnable() {
            private final Logger logger = LoggerFactory.getLogger(this.getClass());

            @Override
            public void run() {
                logger.info("DBWriterService run");
                while (!stop || priceQueue.isEmpty()) {
                    if (priceQueue.isEmpty()) {
                        try {
                            synchronized (monitor) {
                                logger.info("wait");
                                monitor.wait();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    logger.info("try to take");
                    Price price = null;
                    try {
                        price = priceQueue.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (price != null) {
                        logger.info("save");
                        try {
                            priceService.save(price);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
                logger.info("DBWriterService end");

            }
        };

        writerThread = new Thread(writer);
        writerThread.setName("DBWriter");
        writerThread.start();
        /*try {
            writerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

    }


}
