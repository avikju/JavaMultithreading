package com.avik.thread.deadlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AvoidDeadLockExample {

  private static Lock lock1 = new ReentrantLock();
  private static Lock lock2 = new ReentrantLock();

  public static void main(String[] args) {

    new Thread(
            () -> {
              System.out.println("Acquiring... lock1 by t1");
              lock1.lock();
              System.out.println("Acquired lock1 by t1");
              sleep();
              System.out.println("Acquiring... lock2 by t1");
              lock2.lock();

              lock2.unlock();
              lock1.unlock();
            })
        .start();
    new Thread(
            () -> {
              System.out.println("Acquiring... lock2 by t2");
              lock2.lock();
              System.out.println("Acquired lock2 by t2");
              sleep();
              System.out.println("Acquiring... lock1 by t2");
              lock1.lock();

              lock1.unlock();
              lock2.unlock();
            })
        .start();
  }

  private static void sleep() {
    try {
      Thread.sleep(4000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
