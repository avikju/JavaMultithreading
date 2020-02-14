package com.avik.thread.communication.basic;

public class Waiter implements Runnable {

	private Message msg;

	public Waiter(Message m) {
		this.msg = m;
	}

	@Override
	public void run() {
		String name = Thread.currentThread().getName();
		// waiter thread enters into synchronized block
		synchronized (msg) {
			try {
				System.out.println(name + " waiting to get notified at time:" + System.currentTimeMillis());
				// waiter thread gives up the lock on the object msg and goes on waiting, until
				// it gets notified by some other thread having lock on the same msg object
				msg.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// waiter thread is notified now and starts working
			System.out.println(name + " waiter thread got notified at time:" + System.currentTimeMillis());
			// process the message now
			System.out.println(name + " processed: " + msg.getMsg());
		}
	}

}
