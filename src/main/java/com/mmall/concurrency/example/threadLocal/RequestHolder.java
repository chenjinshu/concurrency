package com.mmall.concurrency.example.threadLocal;

// 线程封闭
public class RequestHolder {

    private final static ThreadLocal<Long> requestHolder = new ThreadLocal<>();

    public static void add(Long id) {
        requestHolder.set(id);
    }

    public static Long getId() {
        return requestHolder.get();
    }

    public static void remove() {
        requestHolder.remove();
    }

    /* ThreadLocal内部实际上维护了一个Map对象
     * 当调用set方法时，实际上是把当前线程的id作为key，传入set的参数
     * 作为value进行存储的
     * 当调用get方法时，就是从Map中取当前线程的id的key对应的value值
     * 当调用remove方法时，根据当前线程id移除对应的键值对
     *
     * 实例操作见HttpFilter类*/
}
