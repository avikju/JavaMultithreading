package com.avik.thread.communication.prodcon.custom.blockingqueue;

public class Producer implements Runnable {

	private MyBlockingQueue<Integer> blockingQueue;

	public Producer(MyBlockingQueue<Integer> blockingQueue) {
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
