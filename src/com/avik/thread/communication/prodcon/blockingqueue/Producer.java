package com.avik.thread.communication.prodcon.blockingqueue;

import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {

	private BlockingQueue<Integer> blockingQueue;

	public Producer(BlockingQueue<Integer> blockingQueue) {
		super();
		this.blockingQueue = blockingQueue;
	}

	@Override
	public void run() {
		try {
			int i = 1;
			while (true) {
				blockingQueue.put(i); // thread goes into blocking state if queue is full
				System.out.println(String.format("Produced:%d and queue size is:%d", i, blockingQueue.size()));
				i++;
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
