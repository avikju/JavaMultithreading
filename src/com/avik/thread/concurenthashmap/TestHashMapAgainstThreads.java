/** */
package com.avik.thread.concurenthashmap;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class WriteTask implements Runnable {

  Map<String, String> map;
  int init;
  int end;

  WriteTask(Map<String, String> map, int init, int end) {
    this.map = map;
    this.init = init;
    this.end = end;
  }

  @Override
  public void run() {
    for (int i = init; i < end; i++) {
      String key = "Key-" + i;
      String value = "Value-" + i;
      map.put(key, value);
      System.out.println(
          Thread.currentThread().getName() + " has put (k,v)=" + key + " and " + value);
      try {
        Thread.sleep(2000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}

class ReadTask implements Runnable {

  Map<String, String> map;

  ReadTask(Map<String, String> map) {
    this.map = map;
  }

  @Override
  public void run() {
    while (true) {
      map.forEach(
          (k, v) -> {
            System.out.println(Thread.currentThread().getName() + "hashmap size=" + map.size());
          });
      try {
        Thread.sleep(2000);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }
}
/** @author Avik */
public class TestHashMapAgainstThreads {

  public static void main(String[] args) {
    Map<String, String> concurrentMap = new ConcurrentHashMap<>(20, 1, 2);
    Map<String, String> hashMap = new HashMap<>();
    Thread t_put1 = new Thread(new WriteTask(concurrentMap, 0, 10));
    Thread t_put2 = new Thread(new WriteTask(concurrentMap, 10, 20));
    Thread t_read = new Thread(new ReadTask(concurrentMap));
    t_put1.start();
    t_put2.start();
    t_read.start();
    ;
  }
}
