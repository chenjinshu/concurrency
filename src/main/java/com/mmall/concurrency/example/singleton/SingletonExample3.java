package com.mmall.concurrency.example.singleton;

import com.mmall.concurrency.annotations.NotRecommend;
import com.mmall.concurrency.annotations.ThreadSafe;

@ThreadSafe
@NotRecommend
public class SingletonExample3 {

    // 私有构造函数
    private SingletonExample3() {

    }

    // 单例对象
    private static SingletonExample3 instance = null;     // 饱汉模式（懒汉模式）

    // 静态工厂方法
    public static synchronized SingletonExample3 getInstance() {

        if(instance == null) {
            instance = new SingletonExample3();
        }

        return instance;
    }

    /*
    * 线程安全，但是并不推荐该种写法
    * 原因是由于方法上加了sync关键字，那么同一时间只允许一个线程进入该方法，
    * 在性能上就会受到较大的影响*/

}
