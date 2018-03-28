package com.mmall.concurrency.example.publish;

import com.mmall.concurrency.annotations.NotThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

@NotThreadSafe
public class UnsafePublish {   // 发布对象

    private static Logger log = LoggerFactory.getLogger(UnsafePublish.class);

    private String[] states = {"a", "b", "c"};

    public String[] getStates() {
        return states;
    }

    public static void main(String[] args) {
        UnsafePublish unsafePublish = new UnsafePublish();
        log.info("{}", Arrays.toString(unsafePublish.getStates()));

        unsafePublish.getStates()[0] = "d";
        log.info("{}", Arrays.toString(unsafePublish.getStates()));
    }
}
