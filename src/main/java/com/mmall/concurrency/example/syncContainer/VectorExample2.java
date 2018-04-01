package com.mmall.concurrency.example.syncContainer;

import com.mmall.concurrency.annotations.NotThreadSafe;

import java.util.Vector;

@NotThreadSafe
public class VectorExample2 {

    private static Vector<Integer> vector = new Vector<>();

    public static void main(String[] args) {
        while (true) {
            for (int i = 0; i < 10; i++) {
                vector.add(i);
            }


            Thread t1 = new Thread() {
                public void run() {
                    for (int i = 0; i < vector.size(); i++) {
                        vector.remove(i);
                    }
                }
            };

            Thread t2 = new Thread() {
                public void run() {
                    for (int i = 0; i < vector.size(); i++) {
                        vector.get(i);
                    }
                }
            };

            t1.start();
            t2.start();
        }
    }

    /*
    * 虽然vector里面的方法都是加了sync修饰的，但是这里有可能出现这种情况。
    * 线程2刚执行到第29行，而线程1也执行到第21行，此时两个i值都一样。
    * 然后线程1先将i给remove掉。此时线程2调用get（i）时就会出现数组越界异常*/
}
