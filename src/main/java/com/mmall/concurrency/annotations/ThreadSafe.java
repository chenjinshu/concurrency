package com.mmall.concurrency.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 课程里用来标记【线程安全】的类或者写法
 */
@Target(ElementType.TYPE)    // 注解作用的对象是类
@Retention(RetentionPolicy.SOURCE)   // 编译的时候此注解会被忽视掉，此注解仅作为一个标识作用
public @interface ThreadSafe {
    String value() default "";
}
