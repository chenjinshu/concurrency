package com.mmall.concurrency.example.commonUnsafe;

import com.mmall.concurrency.annotations.NotThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@NotThreadSafe
public class DateFormatExample1 {

    private static Logger log = LoggerFactory.getLogger(DateFormatExample1.class);

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");    // 非线程安全的对象

    // 请求总数
    public static int clientTotal = 5000;

    // 同时并发执行的线程数
    public static int threadTotal = 200;

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal ; i++) {
            executorService.execute(() -> {  // 相当于创建一个线程传递的是run方法执行的函数
                try {
                    semaphore.acquire();    // 获取一个信号，如果信号被线程全部获取完毕，其余线程将在此等待，最多同时被200个线程获取
                    update();
                    semaphore.release();    // 释放信号
                } catch (Exception e) {
                    log.error("  exception:  ", e);
                }
                countDownLatch.countDown();    // 闭锁值减1
            });
        }
        countDownLatch.await();     // 如果闭锁的值大于0，线程将在此等待知道闭锁值为0继续执行
        executorService.shutdown();

    }

    private static void update() {

        try {
            simpleDateFormat.parse("20180208");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
