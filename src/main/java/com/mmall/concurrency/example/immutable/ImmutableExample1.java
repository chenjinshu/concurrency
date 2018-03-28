package com.mmall.concurrency.example.immutable;

import com.google.common.collect.Maps;
import com.mmall.concurrency.annotations.NotThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@NotThreadSafe
public class ImmutableExample1 {

    private static Logger log = LoggerFactory.getLogger(ImmutableExample1.class);

    private final static Integer a = 1;
    private final static String b = "2";
    private final static Map<Integer, Integer> map =Maps.newHashMap();

    static {
        map.put(1, 2);
        map.put(3, 4);
        map.put(5, 6);
    }

    public void test(final int a) {
        // a = 1;   // 不能修改
    }

    public static void main(String[] args) {
        // a = 2;
        // b = "4";
        // map = Maps.newHashMap();
        map.put(1, 3);
        log.info("{}", map.get(1));

        /*
        * 用final修饰的Map仅仅只能保证map不能指向别的引用，但是其指向的Map的内部值是可以更改的，并不能保证
        * map对象的安全 */
    }
}
