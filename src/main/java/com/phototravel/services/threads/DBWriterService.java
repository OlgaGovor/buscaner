package com.phototravel.services.threads;

import com.phototravel.entity.Price;
import com.phototravel.services.PriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;

@Component
public class DBWriterService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    ArrayBlockingQueue<QueuedItemContainer<Price>> priceQueue = new ArrayBlockingQueue<QueuedItemContainer<Price>>(100);
    private volatile boolean stop;
    private Thread writerThread;
    private Object monitor = new Object();

    private Integer totalAddedRowsCount = 0;
    private Integer savedRowsCount = 0;

    @Autowired
    PriceService priceService;


    public void addToQueue(QueuedItemContainer<Price> priceContainer) throws InterruptedException {
        logger.info("addToQueue " + priceContainer.getItem());
        priceQueue.put(priceContainer);
        totalAddedRowsCount++;

        synchronized (monitor) {
            monitor.notifyAll();
        }
    }

    public QueuedItemContainer<Price> take() throws InterruptedException {
        if (priceQueue.isEmpty()) {
            return null;
        }

        logger.info("take queue size=" + priceQueue.size());
        return priceQueue.take();
    }

    public void stopService() {
        stop = true;
        logger.info("init stop");
    }

    public boolean isStarted() {
        return !stop;
    }

    public String getStatistics() {
        return " Current queue size: " + priceQueue.size() +
                " total rows count: " + totalAddedRowsCount +
                " saved rows count: " + savedRowsCount;
    }

    public void startService() {

        if (writerThread == null || !writerThread.isAlive()) {
            stop = false;
            runThread();
        }
    }


    public void runThread() {
        Runnable writer = new Runnable() {
            private final Logger logger = LoggerFactory.getLogger(this.getClass());

            @Override
            public void run() {
                logger.info("DBWriterService - Awaken");
                while (!stop || priceQueue.isEmpty()) {
                    if (priceQueue.isEmpty()) {
                        try {
                            synchronized (monitor) {
                                logger.info("No rows in queue - waiting");
                                monitor.wait();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    logger.info("try to take row");

                    QueuedItemContainer<Price> priceContainer = null;
                    try {
                        priceContainer = priceQueue.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (priceContainer != null && priceContainer.getItem() != null) {
                        Price price = priceContainer.getItem();
                        logger.info("save " + price);
                        try {
                            if (priceService.save(price)) {
                                if (priceContainer.hasCallback()) {
                                    priceContainer.getCallback().taskDone();
                                }
                                savedRowsCount++;
                                logger.info("save OK" + price);
                            } else {
                                logger.info("some problem while saving " + price);
                            }

                        } catch (Exception e) {
                            logger.info("some problem while saving " + price + " " + e.getMessage());
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

    }


}
