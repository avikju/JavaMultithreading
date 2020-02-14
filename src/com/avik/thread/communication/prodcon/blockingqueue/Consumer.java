package com.avik.thread.communication.prodcon.blockingqueue;

import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {

	private BlockingQueue<Integer> blockingQueue;

	public Consumer(BlockingQueue<Integer> blockingQueue) {
		super();
		this.blockingQueue = blockingQueue;
	}

	@Override
	public void run() {
		try {
			while (true) {
				int i = blockingQueue.take(); // thread goes into blocking state if queue is empty
				System.out.println(String.format("Consumed: % d and queue size is:%d", i, blockingQueue.size()));
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
