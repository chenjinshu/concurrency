package com.mmall.concurrency.publish;

import com.mmall.concurrency.annotations.NotThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NotThreadSafe
public class Escape {  // 对象溢出(在对象未完全构造完毕之前就将其发布)

    private static Logger log = LoggerFactory.getLogger(Escape.class);

    private int thisCanBeEscape = 0;

    public Escape() {
        new InnerClass();
    }

    private class InnerClass {
        public InnerClass() {
            log.info("{}", Escape.this.thisCanBeEscape);
        }
    }

    public static void main(String[] args) {
        new Escape();
    }

    /*
    * 当调用new Escape()时，进入Escape构造方法，这时在构造方法里由调用了new InnerClass()
    * 来创建一个内部类，此时进入了InnerClass构造方法，在这个构造方法里🈶️又调用里Escape.this
    * 来获取外部类对象，而此时scape的构造方法还没有执行完毕，也就是外部类对象还没有完全
    * 构造完成，此时当调用外部类对象的一些变量时就有可能会出现问题，这就是对象溢出 */
}
