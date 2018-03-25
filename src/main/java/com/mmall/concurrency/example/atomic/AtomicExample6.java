package com.mmall.concurrency.example.atomic;

import com.mmall.concurrency.annotations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicStampedReference;

@Slf4j
@ThreadSafe
public class AtomicExample6 {

    private static Logger log = LoggerFactory.getLogger(AtomicExample6.class);

    //private static AtomicInteger atomicInteger = new AtomicInteger(100);

    private static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(200, 0);

    private static CountDownLatch countDownLatch = new CountDownLatch(2);

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();
        for(int i = 0; i < 2; i++) {
            if(i == 0) {
                executorService.execute(() -> {

                    int stamp = atomicStampedReference.getStamp();
                    Integer reference = atomicStampedReference.getReference();
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    log.info("200变为203" + atomicStampedReference.compareAndSet(reference, 203, stamp, stamp + 1));
                    stamp = atomicStampedReference.getStamp();
                    reference = atomicStampedReference.getReference();
                    log.info("203变为200" + atomicStampedReference.compareAndSet(reference, 200, stamp, stamp + 1));
                    log.info("atomicStampedReference ==== {}", atomicStampedReference.getReference());

                    countDownLatch.countDown();
                });
            } else {
                executorService.execute(() -> {

                    int stamp = atomicStampedReference.getStamp();
                    try {
                        TimeUnit.SECONDS.sleep(2);
                        log.info("atomicStampedReference当前值为："  + atomicStampedReference.getReference());
                        log.info("200变为205" + atomicStampedReference.compareAndSet(200, 205, stamp, stamp + 1));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    log.info("atomicStampedReference **** {}", atomicStampedReference.getReference());
                    countDownLatch.countDown();
                });
            }
        }

        countDownLatch.await();
        executorService.shutdown();
    }

    /*
    * 从执行结果可以看出，当线程1将数值由200变为203，再由203变为200后，线程后试图再把200变为205时，由于其时间戳
     * 已经和最初获得的时间戳不一致，所以导致更新失败，用于解决cas中的ABA问题 */

}
