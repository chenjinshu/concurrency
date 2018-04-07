package com.mmall.concurrency.example.threadPool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExample4 {

    private static Logger log = LoggerFactory.getLogger(ThreadPoolExample4.class);

    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);

//        scheduledExecutorService.schedule(new Runnable() {
//            @Override
//            public void run() {
//                log.warn("schedule run!");
//            }
//        }, 3, TimeUnit.SECONDS);      // 延迟3秒后执行

        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                log.info("schedule run!");
            }
        }, 3, 5, TimeUnit.SECONDS);


        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                log.info("timer run~");
            }
        }, new Date(), 5 * 1000);

        //log.info("shutdown～");
        //scheduledExecutorService.shutdown();

        /*
         
         */
    }
}
