package com.avik.thread.communication.pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MyThreadPool {

  private BlockingQueue<Runnable> queue;

  public MyThreadPool(int threadPoolSize) {
    queue = new ArrayBlockingQueue<>(threadPoolSize);
    Runnable r = new Worker();
    for (int i = 0; i < threadPoolSize; i++) {
      (new Thread(r)).start();
    }
  }

  public void execute(Runnable task) throws InterruptedException {
    queue.put(task);
  }

  private class Worker implements Runnable {

    @Override
    public void run() {
      Runnable task = null;
      while (true) {
        try {
          task = queue.take();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        task.run();
      }
    }
  }
}
