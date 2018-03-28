package com.mmall.concurrency.example.singleton;

import com.mmall.concurrency.annotations.ThreadSafe;

@ThreadSafe
public class SingletonExample6 {

    // 私有构造函数
    private SingletonExample6() {

    }

    // 单例对象
    private static SingletonExample6 instance = null;   // 饿汉模式（通过采用静态块的初始化方式)

    static {
        instance = new SingletonExample6();
    }

    // 静态工厂方法
    public static SingletonExample6 getInstance() {

        return instance;
    }

    public static void main(String[] args) {
        System.out.println(getInstance());
        System.out.println(getInstance());
    }

    /*
    * 采用这种方式需要注意的是静态变量的声明和静态初始化块的顺序，
    * 哪个声明在前哪个先执行，如果将静态初始化块放在静态变量的声明之前，
    * 则打印的结果会是null
    * */

}
