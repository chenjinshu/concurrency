package com.mmall.concurrency.example.aqs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureExample {

    private static Logger log = LoggerFactory.getLogger(FutureExample.class);

    static class MyCallable implements Callable<String> {

        @Override
        public String call() throws Exception {
            log.info("do something~");
            Thread.sleep(5000);
            return "done";
        }
    }

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<String> future = executorService.submit(new MyCallable());
        log.info("do something in main~");
        Thread.sleep(1000);
        String result = future.get();
        log.info("result: {}", result);
    }

    /*
    * 这里的Callable其实就类似于Runnable，call方法其实就类似于
    * run方法，不同的是call方法会有一个返回值，当调用executorService.submit
    * 方式时，会返回一个Future对象，当call方法执行完毕后，返回结果被写入返回的
    * future对象中。当调用future.get方法时，如果没有取到值，说明call方法还没有
    * 执行完毕，线程会在这里等待，直到call方法结束并把返回值保存到了futrue中。*/
}
