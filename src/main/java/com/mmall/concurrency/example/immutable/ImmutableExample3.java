package com.mmall.concurrency.example.immutable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.mmall.concurrency.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;

@ThreadSafe
public class ImmutableExample3 {

    private static Logger log = LoggerFactory.getLogger(ImmutableExample3.class);

    private static final ImmutableList immutableList = ImmutableList.of(1, 2, 3, 2);

    private static final ImmutableSet immutableSet = ImmutableSet.copyOf(immutableList);

    private static final ImmutableMap immutableMap = ImmutableMap.of(1, "a", 2, "b");

    private static final ImmutableMap immutableMap2 = ImmutableMap.<Integer, String>builder().put(3, "c").put(4, "d").build();  // 另一种初始化方式

    public static void main(String[] args) {
       // immutableList.add(4);    // 运行时抛出异常
       // immutableSet.add(4)      // 运行时抛出异常
       // immutableMap.put(1, "p");    // 运行时抛出异常
       // immutableMap2.put(4, "u");    // 运行时抛出异常
        System.out.println(immutableMap.get(1));
    }
}
