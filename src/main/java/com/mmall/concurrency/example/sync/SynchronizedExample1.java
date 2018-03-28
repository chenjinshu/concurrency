package com.mmall.concurrency.example.sync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SynchronizedExample1 {

    private Logger log = LoggerFactory.getLogger(SynchronizedExample1.class);

    public void test1() {

        // 修饰一个代码块
        synchronized (this) {
            for(int i=0; i<10 ;i++) {
                log.info("test1 - {}", i);
            }
        }

    }

    // 修饰一个方法
    public synchronized void test2() {
        for(int i=0; i<10 ;i++) {
            log.info("test2 - {}", i);
        }
    }

    public static void main(String[] args) {
        SynchronizedExample1 synchronizedExample1 = new SynchronizedExample1();
        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.execute(() -> {
            synchronizedExample1.test1();
        });

        executorService.execute(() -> {
            synchronizedExample1.test1();
        });

        executorService.shutdown();
    }
}
