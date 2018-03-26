package com.mmall.concurrency.sync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SynchronizedExample2 {

    private static Logger log = LoggerFactory.getLogger(SynchronizedExample2.class);

    public static void test1() {

        // 修饰一个代码块
        synchronized (SynchronizedExample2.class) {
            for(int i=0; i<10 ;i++) {
                log.info("test1 - {}", i);
            }
        }

    }

    // 修饰一个静态方法
    public static synchronized void test2() {
        for(int i=0; i<10 ;i++) {
            log.info("test2 - {}", i);
        }
    }

    public static void main(String[] args) {
        SynchronizedExample2 synchronizedExample1 = new SynchronizedExample2();
        SynchronizedExample2 synchronizedExample2 = new SynchronizedExample2();
        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.execute(() -> {
            synchronizedExample1.test1();
        });

        executorService.execute(() -> {
            synchronizedExample2.test1();
        });

        executorService.shutdown();
    }
}
