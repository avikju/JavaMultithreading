package com.avik.thread.communication.prodcon.custom.blockingqueue;

public class Client {

	public static void main(String... a) {

		MyBlockingQueue<Integer> queue = new MyBlockingQueue<Integer>(10); // or LinkedBlockingQueue

		Thread p = new Thread(new Producer(queue));
		Thread c = new Thread(new Consumer(queue));

		p.start();
		c.start();

	}

}
