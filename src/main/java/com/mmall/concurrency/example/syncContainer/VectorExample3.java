package com.mmall.concurrency.example.syncContainer;

import com.mmall.concurrency.annotations.NotThreadSafe;

import java.util.Iterator;
import java.util.Vector;

public class VectorExample3 {

    // java.util.ConcurrentModificationException
    private static void test1(Vector<Integer> v1) {      // foreach
        for(Integer i : v1) {
            if(i.equals(3)) {
                v1.remove(i);
            }
        }
    }

    // java.util.ConcurrentModificationException
    private static void test2(Vector<Integer> v1) {      // iterator
        Iterator<Integer> it = v1.iterator();
        while(it.hasNext()) {
            Integer i = it.next();
            if(i.equals(3)) {
                v1.remove(i);
            }
        }
    }

    // success
    private static void test3(Vector<Integer> v1) {      // for
        for(int i = 0; i < v1.size(); i++) {
            if(v1.get(i).equals(3)) {
                v1.remove(v1.get(i));
            }
        }
    }

    public static void main(String[] args) {
        Vector<Integer> vector = new Vector<>();
        vector.add(1);
        vector.add(2);
        vector.add(3);

        test1(vector);
    }

    /*
    * 使用foreach和iterator进行迭代操作时，不要进行更新操作*/

    /*
    * 同步容器并不能做到完全的线程安全，其使用场景也变得越来越少，
    * 更多情况下选择使用并发容器来取代同步容器 */

}
