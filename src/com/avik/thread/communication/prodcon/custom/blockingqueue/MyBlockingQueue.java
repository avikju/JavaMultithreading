package com.avik.thread.communication.prodcon.custom.blockingqueue;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MyBlockingQueue<T> {

	private Queue<T> queue;
	private int maxSize;
	private ReentrantLock lock = new ReentrantLock(true); // lock to lock the queue object
	private Condition full = lock.newCondition(); // condition to wait and notify
	private Condition empty = lock.newCondition(); // condition to wait and notify

	public MyBlockingQueue(int size) {
		this.queue = new LinkedList<T>();
		this.maxSize = size;
	}

	public void put(T t) throws InterruptedException {
		lock.lock();
		try {
			// check if the queue is full or not
			if (queue.size() == maxSize) {
				// if queue is full then wait
				full.await();
			}
			// wait is over as consumer thread has sent signal that queue is not full
			queue.add(t);
			// send signal to empty condition
			empty.signalAll();
		} finally {
			lock.unlock();
		}
	}

	public T take() throws InterruptedException {
		lock.lock();
		try {
			// keep on checking if the queue is empty or not
			// while loop is required as there could be more than one consumer thread were
			// waiting. Now, when they are notified. One thread, acquires the lock and poll
			// one object and make the que empty.
			// then the other thread, gets the lock and starts from polling line. Then it
			// won't get the object. So, we make another thread to check again and go to
			// wait state if queue is empty (it will continue checking the emptiness of the
			// queue until it finds the queue empty).
			while (queue.isEmpty()) {
				empty.await();
			}
			T t = queue.poll();
			full.signalAll();
			return t;
		} finally {
			lock.unlock();
		}

	}

	public int size() {
		return queue.size();
	}
}
