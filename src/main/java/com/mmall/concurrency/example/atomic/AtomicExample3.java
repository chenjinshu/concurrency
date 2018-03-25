package com.mmall.concurrency.example.atomic;

import com.mmall.concurrency.annotations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

@Slf4j
@ThreadSafe
public class AtomicExample3 {

    private static Logger log = LoggerFactory.getLogger(AtomicExample3.class);

    // 请求总数
    public static int clientTotal = 5000;

    // 同时并发执行的线程数
    public static int threadTotal = 200;

    public static LongAdder count = new LongAdder();

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
                    log.error(" exception : ", e);
                }
                countDownLatch.countDown();    // 闭锁值减1
            });
        }
        countDownLatch.await();     // 如果闭锁的值大于0，线程将在此等待知道闭锁值为0继续执行
        executorService.shutdown();
        log.info("count:{}", count);
    }

    private static void add() {
        count.increment();
    }


    /**
     AtomicLong采用CAS算法，死循环内不断尝试修改目标值直到修改成功，如果竞争不激烈的时候修改成功概率很高。
     竞争激烈的时候修改失败的概率很高。不断尝试就会影响性能。long，double jvm 允许将64位的读操作或者写操作拆成2个32位的操作。
     LongAdder的实现思想：热点数据分离。把atomicLong value分离为数组，每个线程访问时通过hash等算法映射到其中一个数组计数，
     最终的计数结果是数组的求和累加，热点数据value就被分离成多个单元，提高了并行度。高并发分散提高性能，但准确度会有偏差。
     序列号生成的情况，准确的数值的情况，全局统一计数的情况还是应该选择atomic
     */

}
