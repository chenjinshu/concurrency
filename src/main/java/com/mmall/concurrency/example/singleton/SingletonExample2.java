package com.mmall.concurrency.example.singleton;

import com.mmall.concurrency.annotations.ThreadSafe;

@ThreadSafe
public class SingletonExample2 {

    // 私有构造函数
    private SingletonExample2() {

    }

    // 单例对象
    private static SingletonExample2 instance = new SingletonExample2();   // 饿汉模式

    // 静态工厂方法
    public static SingletonExample2 getInstance() {

        return instance;
    }

    /*
    * 饿汉模式虽然线程安全，但是如果在构造函数中构造过程比较复杂，会导致该类加载的比较慢，
    * 性能会受到影响。并且如果该instance实例并不会被调用到，也会造成资源的浪费。
    * */

}
