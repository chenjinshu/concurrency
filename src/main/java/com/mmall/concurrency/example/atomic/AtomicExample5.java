package com.mmall.concurrency.example.atomic;

import com.mmall.concurrency.annotations.ThreadSafe;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@ThreadSafe
public class AtomicExample5 {

    private static Logger log = LoggerFactory.getLogger(AtomicExample5.class);

    private static AtomicIntegerFieldUpdater<AtomicExample5> updater = AtomicIntegerFieldUpdater.newUpdater(AtomicExample5.class, "count");

    // @Getter  // 添加get方法（需要安装lombook插件，否则ide会提示错误，但运行不会报错）
    private volatile int count = 100;   // 必须为volatile和非static类型，否则运行会报错

    public int getCount() {
        return count;
    }

    public static void main(String[] args) {

        AtomicExample5 atomicExample5 = new AtomicExample5();

        if (updater.compareAndSet(atomicExample5, 100 ,120)) {
            log.info("count:{}", atomicExample5.getCount());
        }

        if (updater.compareAndSet(atomicExample5, 100 ,120)) {
            log.info("count--:{}", atomicExample5.getCount());
        }
    }

    /*
    * 实际开发中AtomicIntegerFieldUpdater用的并不多 */

}
