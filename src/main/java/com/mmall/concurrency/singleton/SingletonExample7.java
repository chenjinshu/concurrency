package com.mmall.concurrency.singleton;

import com.mmall.concurrency.annotations.Recommend;
import com.mmall.concurrency.annotations.ThreadSafe;

/* 枚举模式：最安全 */
@ThreadSafe
@Recommend
public class SingletonExample7 {

    // 私有构造函数
    private SingletonExample7() {

    }

    // 静态工厂方法
    public static SingletonExample7 getInstance() {

        return Singleton.INSTANCE.getSingleton();
    }

    private enum Singleton {
        INSTANCE;

        private SingletonExample7 singleton;

        // JVM保证这个构造方法绝对只被执行一次，确保多线程情况下也能保证线程安全
        Singleton() {
            singleton = new SingletonExample7();
        }

        public SingletonExample7 getSingleton() {
            return singleton;
        }
    }

    public static void main(String[] args) {
        System.out.println(getInstance());
        System.out.println(getInstance());
    }

    /*
    * 采用枚举类方式获取instance单例，推荐使用这种方式
    * 因为相比懒汉模式，这种方式的安全性更有保障。并且在
    * 性能方面，也是仅在调用类的getInstance方法时，该实例
    * 才会真正被创建，不会造成资源的浪费，所以有更好的性能。
    * */

}
