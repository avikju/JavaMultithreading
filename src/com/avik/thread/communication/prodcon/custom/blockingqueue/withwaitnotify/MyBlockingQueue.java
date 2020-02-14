package com.avik.thread.communication.prodcon.custom.blockingqueue.withwaitnotify;

import java.util.LinkedList;
import java.util.Queue;

public class MyBlockingQueue<T> {

  private Queue<T> queue;
  private int maxSize;

  public MyBlockingQueue(int size) {
    this.queue = new LinkedList<T>();
    this.maxSize = size;
  }

  public synchronized void put(T t) throws InterruptedException {
    // check if the queue is full or not
    if (queue.size() == maxSize) {
      // if queue is full then wait
      this.wait();
    }
    // wait is over as consumer thread has sent signal that queue is not full
    queue.add(t);
    // send signal to empty condition
    this.notifyAll();
  }

  public synchronized T take() throws InterruptedException {

    // keep on checking if the queue is empty or not
    // while loop is required as there could be more than one consumer thread were
    // waiting. Now, when they are notified. One thread, acquires the lock and poll
    // one object and make the queue empty.
    // then the other thread, gets the lock and starts from polling line. Then it
    // won't get the object. So, we make another thread to check again and go to
    // wait state if queue is empty (it will continue checking the emptiness of the
    // queue until it finds the queue empty).
    while (queue.isEmpty()) {
      this.wait();
    }
    T t = queue.poll();
    this.notifyAll();
    return t;
  }

  public int size() {
    return queue.size();
  }
}
