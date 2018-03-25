package com.mmall.concurrency.example.atomic;

import com.mmall.concurrency.annotations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@ThreadSafe
public class AtomicExample2 {

    private static Logger log = LoggerFactory.getLogger(AtomicExample2.class);

    // 请求总数
    public static int clientTotal = 5000;

    // 同时并发执行的线程数
    public static int threadTotal = 200;

    public static AtomicLong count = new AtomicLong(0);

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal ; i++) {
            executorService.execute(() -> {  //  相当于创建一个线程传递的是run方法执行的函数
                try {
                    semaphore.acquire();    // 获取一个信号，如果信号被线程全部获取完毕，其余线程将在此等待，最多同时被200个线程获取
                    add();
                    semaphore.release();     // 释放信号
                } catch (Exception e) {
                    log.error(" exception  ", e);
                }
                countDownLatch.countDown();    // 闭锁值减1
            });
        }
        countDownLatch.await();     // 如果闭锁的值大于0，线程将在此等待知道闭锁值为0继续执行
        executorService.shutdown();
        log.info("count:{}", count.get());
    }

    private static void add() {
        count.incrementAndGet();
    }

    /*
    *  当调用incrementAndGet方法时，会首先根据该对象的值的偏移地址将该对象的值从主存拷贝到工作内存，
    *  然后调用CAS算法：根据偏移地址重新从主存获得最新值，然后与工作内存的值进行比较，如果一样，说明
    *  该值没有被其他线程修改，执行更新操作。由于CAS算法是原子性操作，所以是线程安全的算法。
    **/
}
