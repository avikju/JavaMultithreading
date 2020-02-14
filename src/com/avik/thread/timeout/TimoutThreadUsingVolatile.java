package com.avik.thread.timeout;

class MyTask implements Runnable {

  public volatile boolean keepRunning = true;

  @Override
  public void run() {
    while (keepRunning) {
      for (int i = 0; i < 1000; i++) {
        System.out.println("I am thread. Value is=" + i);
      }
    }
    System.out.println("stopping thread");
  }
}

public class TimoutThreadUsingVolatile {

  public static void main(String a[]) throws InterruptedException {
    MyTask myTask = new MyTask();
    Thread t = new Thread(myTask);
    t.start();
    Thread.sleep(10);
    myTask.keepRunning = false;
  }
}
