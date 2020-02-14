package com.avik.thread.timeout;

public class TimeoutThread {

  public static void main(String[] args) throws InterruptedException {

    Thread t =
        new Thread(
            () -> {
              while (!Thread.interrupted()) {
                for (int i = 0; i < 1000; i++) {
                  System.out.println("I am thread-" + i);
                }
              }
              System.out.println("Stopping the thread");
              return;
            });

    t.start();
    Thread.sleep(500);
    t.interrupt();
  }
}
