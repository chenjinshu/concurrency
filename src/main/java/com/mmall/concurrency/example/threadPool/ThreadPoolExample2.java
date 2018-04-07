package com.mmall.concurrency.example.threadPool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolExample2 {

    private static Logger log = LoggerFactory.getLogger(ThreadPoolExample2.class);

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        for(int i = 0; i < 10; i++) {
            final int index = i;
            executorService.execute(new Runnable(){

                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    log.info("task : {}", index);
                }
            });
        }

        log.info("shutdown～");
        executorService.shutdown();
        //executorService.shutdownNow();
    }
}
