package com.avik.thread.communication.prodcon.blockingqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Client {

	public static void main(String... a) {

		BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(10); // or LinkedBlockingQueue

		Thread p = new Thread(new Producer(queue));
		Thread c = new Thread(new Consumer(queue));

		p.start();
		c.start();

	}

}
