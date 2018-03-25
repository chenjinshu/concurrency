package com.mmall.concurrency.example.atomic;

import com.mmall.concurrency.annotations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.LongAdder;

@Slf4j
@ThreadSafe
public class AtomicExample4 {

    private static Logger log = LoggerFactory.getLogger(AtomicExample4.class);

    private static AtomicReference<Integer> count = new AtomicReference<>(0);

    public static void main(String[] args) {
        System.out.println(count.compareAndSet(0, 2));
        System.out.println(count.compareAndSet(0, 1));
        System.out.println(count.compareAndSet(1, 3));
        System.out.println(count.compareAndSet(2, 4));
        System.out.println(count.compareAndSet(3, 5));
        log.info("count:{}", count.get());
    }

}
