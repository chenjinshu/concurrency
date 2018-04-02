package com.mmall.concurrency.example.lock;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LockExample6 {

    private static Logger log = LoggerFactory.getLogger(LockExample6.class);

    public static void main(String[] args) {
        ReentrantLock reentrantLock = new ReentrantLock();
        Condition condition = reentrantLock.newCondition();

        new Thread(() -> {
            try {
                reentrantLock.lock();
                log.info("wait signal"); // 1
                condition.await();   // 会释放掉reentrantLock的锁
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("get signal"); // 4
            reentrantLock.unlock();
        }).start();

        new Thread(() -> {
            reentrantLock.lock();
            log.info("get lock"); // 2
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            condition.signalAll();     // 唤醒所有在condition上等待的线程，让他们去争取reentrantLock的锁，但本身不会释放锁
            log.info("send signal ~ "); // 3
            reentrantLock.unlock();
        }).start();
    }

    /*
    * condition的await和signal等方法其实就类似于object.await和notify等方法
    * Condition用的地方也不多，了解了解。。 */
}