package com.mmall.concurrency.example.aqs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Time;
import java.util.concurrent.*;

public class CyclicBarrierExample1 {

    private static Logger log = LoggerFactory.getLogger(CyclicBarrierExample1.class);

    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(5);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for(int i = 0; i < 10; i++) {
            final int threadNum = i;
            Thread.sleep(1000);
            executorService.execute(() -> {
                try {
                    race(threadNum);
                } catch (Exception e) {
                    log.info("exception : ", e);
                }

            });
        }

        executorService.shutdown();
    }

    private static void race(int threadNum) throws Exception {
        Thread.sleep(1000);
        log.info("{} is ready", threadNum);
        cyclicBarrier.await();
        log.info("{} continue", threadNum);
    }

    /*
    * 当有5个线程都调用了cyclicBarrier.await()方法后，其后面都代码才会执行，同时cyclicBarrier
    * 计数被清空，可以再次循环使用 */
}
