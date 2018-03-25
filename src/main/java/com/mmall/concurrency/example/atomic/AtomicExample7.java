package com.mmall.concurrency.example.atomic;

import com.mmall.concurrency.annotations.NotThreadSafe;
import com.mmall.concurrency.annotations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

@Slf4j
@NotThreadSafe
public class AtomicExample7 {

    private static Logger log = LoggerFactory.getLogger(AtomicExample7.class);

    // 请求总数
    public static int clientTotal = 50000;

    // 同时并发执行的线程数
    public static int threadTotal = 2000;

    private static int count = 0;

    private static AtomicBoolean isHappened = new AtomicBoolean(false);

    private static boolean flag = false;

    private static void add() {
        if(isHappened.compareAndSet(false, true)) {
            count++;
        }

//        if(flag == false) {
//            count++;
//            flag = true;
//        }
    }

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();

        final Semaphore semaphore = new Semaphore(threadTotal);

        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);

        for(int k = 0; k < clientTotal; k++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    add();
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                countDownLatch.countDown();
            });
        }

        countDownLatch.await();
        executorService.shutdown();

        log.info("count:{}", count);

    }

    /*
    * 始终为1, 如果换成注释中的那种写法，则很可能大于1  */

}
