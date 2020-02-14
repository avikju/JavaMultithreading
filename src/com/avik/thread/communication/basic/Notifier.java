
package com.avik.thread.communication.basic;

public class Notifier implements Runnable {

	private Message msg;

	public Notifier(Message msg) {
		this.msg = msg;
	}

	@Override
	public void run() {
		String name = Thread.currentThread().getName();
		System.out.println(name + " started");
		try {
			Thread.sleep(1000);
			// notifier thread acquires the lock on object msg and enters into synchronized
			// block
			synchronized (msg) {
				msg.setMsg(name + " Notifier work done");
				// only one thread will be notified which was waiting on the same object (msg)
				msg.notify();
				// all threads who were waiting on the same object (msg) will be notified
				// msg.notifyAll();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
