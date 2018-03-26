package com.mmall.concurrency.example.count;

import com.mmall.concurrency.annotations.NotThreadSafe;
import com.mmall.concurrency.annotations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Slf4j
@NotThreadSafe
public class CountExample4 {

    private static Logger log = LoggerFactory.getLogger(CountExample4.class);

    // 请求总数
    public static int clientTotal = 5000;

    // 同时并发执行的线程数
    public static int threadTotal = 200;

    public static volatile int count = 0;

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal ; i++) {
            executorService.execute(() -> {  // 相当于创建一个线程传递的是run方法执行的函数
                try {
                    semaphore.acquire();    // 获取一个信号，如果信号被线程全部获取完毕，其余线程将在此等待，最多同时被200个线程获取
                    add();
                    semaphore.release();    // 释放信号
                } catch (Exception e) {
                    log.error("  exception:  ", e);
                }
                countDownLatch.countDown();    // 闭锁值减1
            });
        }
        countDownLatch.await();     // 如果闭锁的值大于0，线程将在此等待知道闭锁值为0继续执行
        executorService.shutdown();
        log.info("count:{}", count);
    }

    private static void add() {
        count++;
    }

    /* volatile并不能保证线程安全
    *  在这里，count++实际上包含3步操作。
    *  1：从主存取出count的值放入工作内存。
    *  2：将工作内存的count值加1。
    *  3：将工作内存的count的值更新回主内存。
    *  虽然volatile关键字使得每次读取变量时都是从主内存获得的值，
    *  并且会强迫在第2步完成后立即执行第3步，也就相当于2，3步变成了一个
    *  原子操作。但是由于第1步和第2步并不是原子操作，假如两个线程同时读取了count变量，
    *  那么最终的值实际上也就仅仅被加了1。
    *
    *  既然volatile并不能保证原子性，那么其适用场景是什么呢？
    *  一般来说，当对某一个变量的修改并不依赖于该变量的当前值时
    *  （换句话说，我在对某个变量修改时，并不关心这个变量之前的值是什么，直接修改）
    *  通常用volatile修饰的变量一般用在标志位的判断上。
    * */
}
