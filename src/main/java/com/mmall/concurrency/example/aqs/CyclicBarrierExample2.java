package com.mmall.concurrency.example.aqs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CyclicBarrierExample2 {

    private static Logger log = LoggerFactory.getLogger(CyclicBarrierExample2.class);

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
                    log.info("exception  : ", e);
                }

            });
        }

        executorService.shutdown();
    }

    private static void race(int threadNum) throws Exception {
        Thread.sleep(1000);
        log.info("{} is ready", threadNum);
        try {
            cyclicBarrier.await(2000, TimeUnit.MILLISECONDS);
        } catch(Exception e) {
            log.info("Exception:", e);
        }
        log.info("{} continue", threadNum);
    }

    /*
    * 当调用cyclicBarrier.await()方法时，可以设置等待时间，如果
     * 在指定时间后cyclicBarrier的计数仍然没满，则不再等待，代码
     * 的代码可以继续执行 */
}
