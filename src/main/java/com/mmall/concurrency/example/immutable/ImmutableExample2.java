package com.mmall.concurrency.example.immutable;

import com.google.common.collect.Maps;
import com.mmall.concurrency.annotations.NotThreadSafe;
import com.mmall.concurrency.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;

@ThreadSafe
public class ImmutableExample2 {

    private static Logger log = LoggerFactory.getLogger(ImmutableExample2.class);

    private static Map<Integer, Integer> map =Maps.newHashMap();

    static {
        map.put(1, 2);
        map.put(3, 4);
        map.put(5, 6);
        map = Collections.unmodifiableMap(map);   // map真正变得不可变（这里的不可变指的是对象内容不可变，但map引用的指向是可变的）
    }

    public static void main(String[] args) {
        // map.put(1, 3);   //运行时直接抛出异常，不允许这样做。
        log.info("{}", map.get(1));

    }
}
