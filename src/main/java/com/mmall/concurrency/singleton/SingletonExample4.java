package com.mmall.concurrency.singleton;

import com.mmall.concurrency.annotations.NotRecommend;
import com.mmall.concurrency.annotations.NotThreadSafe;
import com.mmall.concurrency.annotations.Recommend;
import com.mmall.concurrency.annotations.ThreadSafe;

@NotThreadSafe
public class SingletonExample4 {

    // 私有构造函数
    private SingletonExample4() {

    }

    // 单例对象
    private static SingletonExample4 instance = null;     // 饱汉模式（懒汉模式）

    // 静态工厂方法
    public static SingletonExample4 getInstance() {

        if(instance == null) {
            synchronized (SingletonExample4.class) {
                if(instance == null) {
                    instance = new SingletonExample4();
                }
            }
        }

        return instance;
    }

    /*
      双重同步锁单例模式
    * 线程不安全，推荐该种写法
    * 原因是通过下沉sync关键字，并且假如了双重检测机制，对性能的影响会大大降低 */

    /*
    * 那么为什么这种写法是线程不安全的呢，明明已经加了sync关键字修饰了啊？？！！
    * 这就得从cpu指令说起了。
    * 当执行instance = new SingletonExample4()操作时，实际上包含3不操作。
    * 1：memory = allocate() 分配对象的内存空间。
    * 2：ctorInstance() 初始化对象
    * 3：instance = memory 设置instance指向刚分配的内存
    * 在多线程情况下，由于第2步和第3步不具有依赖关系，如果由于jvm和cpu的优化产生了指令重排，
    * 实际执行顺序变成了
    * 1：memory = allocate() 分配对象的内存空间。
    * 3：instance = memory 设置instance指向刚分配的内存
    * 2：ctorInstance() 初始化对象
    *
    * 如果现在有两个线程，线程a执行到了指令重排后的instance = memory步骤，
    * 而线程b此时刚好进入到了第23行的if判断。由于此时instance = memory已经执行
    * 完毕了，所以线程b的该if判断返回false，从而直接返回了instance，但是实际上此时
    * 该instance实例是还没有被创建的，instance指向的仅仅是一块空内存而已。如果此时
    * 调用instance的某个方法便会出现问题。
    * 所以该双重检测机制并不是线程安全的。
    * */

}
