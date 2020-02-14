package com.avik.thread.concurrent.collections;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentMapTesting {

  public static void main(String[] args) {
    Map<String, String> hashMap = new HashMap<String, String>();
    Map<String, String> synchronizedMap = Collections.synchronizedMap(new HashMap<>());
    ExecutorService executors = Executors.newFixedThreadPool(10);
    for (int i = 1; i < 60; i++) {
      (new Thread(new Writer(hashMap))).start();
    }
    hashMap.forEach(
        (k, v) -> {
          System.out.println("Key=" + k + " and value=" + v);
        });
  }
}

class Writer implements Runnable {

  private Map<String, String> map;

  public Writer(Map<String, String> map) {
    super();
    this.map = map;
  }

  @Override
  public void run() {
    // generate random number between 1 to 1000
    int x = new Random().nextInt((1000 - 1) + 1) + 1;
    map.put("key-" + x, "value-" + x);
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) { // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
