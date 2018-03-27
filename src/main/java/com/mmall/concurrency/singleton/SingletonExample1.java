package com.mmall.concurrency.singleton;

import com.mmall.concurrency.annotations.NotThreadSafe;

@NotThreadSafe
public class SingletonExample1 {

    // 私有构造函数
    private SingletonExample1() {

    }

    // 单例对象
    private static SingletonExample1 instance = null;    // 饱汉模式（懒汉模式）

    // 静态工厂方法
    public static SingletonExample1 getInstance() {

        if(instance == null) {
            instance = new SingletonExample1();
        }

        return instance;
    }

    /*
    * 线程不安全 */

}
