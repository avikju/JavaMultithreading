package com.avik.thread.communication.atomicinteger;

class AtomicIntTestThread implements Runnable {
  private int count;

  @Override
  public void run() {
    for (int i = 0; i < 5; i++) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) { // TODO Auto-generated catch block
        e.printStackTrace();
      }
      count++;
    }
  }

  public int getCount() {
    return count;
  }
}

public class AtomicIntTest {
  public static void main(String[] args) throws InterruptedException {
    AtomicIntTestThread a1 = new AtomicIntTestThread();
    Thread t1 = new Thread(a1);
    t1.start();
    Thread t2 = new Thread(a1);
    t2.start();
    t1.join();
    t2.join();
    System.out.println(a1.getCount());
  }
}
