package com.mmall.concurrency.example.singleton;

import com.mmall.concurrency.annotations.Recommend;
import com.mmall.concurrency.annotations.ThreadSafe;

@ThreadSafe
@Recommend
public class SingletonExample5 {

    // 私有构造函数
    private SingletonExample5() {

    }

    // 单例对象  volatile + 双重检测机制  -> 禁止指令重排
    private static volatile SingletonExample5 instance = null;     // 饱汉模式（懒汉模式）

    // 静态工厂方法
    public static SingletonExample5 getInstance() {

        if(instance == null) {
            synchronized (SingletonExample5.class) {
                if(instance == null) {
                    instance = new SingletonExample5();
                }
            }
        }

        return instance;
    }

    /*
     * 既然双重检测机制是线程不安全的，那么有没有办法让它线程安全呢？
       有的。。。。只需在instance变量的声明上添加volatile关键字即可，
       该关键字可以阻止指令重排序 */

}
