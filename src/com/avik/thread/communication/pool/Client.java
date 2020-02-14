package com.avik.thread.communication.pool;

public class Client {

	public static void main(String[] args) throws InterruptedException {
		MyThreadPool threadPool = new MyThreadPool(5);

		for (int i = 0; i < 20; i++) {
			int id = i;
			// Runnable is a functional interface in Java 8 (@FuntionalInterface)
			// so, we are using lambda to create Runnable implementation
			Runnable task = () -> System.out.println("I am task: " + id);
			threadPool.execute(task);
		}
	}

}
