package com.mmall.concurrency.example.aqs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class FutrueTaskExample {

    private static Logger log = LoggerFactory.getLogger(FutrueTaskExample.class);

    public static void main(String[] args) throws Exception {
        FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                log.info("do something~");
                Thread.sleep(5000);
                return "done";
            }
        });

        //new Thread(futureTask).start();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(futureTask);
        log.info("do something in main~");
        Thread.sleep(1000);
        String result = futureTask.get();
        log.info("result: {}", result);

        /*
        * 首先创建一个futrueTask任务，然后启动一个线程来执行
        * 这个任务。并且返回结果也直接保存在了futureTask中，更加方便了
        * 如果不采用线程池的方式，直接使用第25行代码也是可以的 */
    }
}
