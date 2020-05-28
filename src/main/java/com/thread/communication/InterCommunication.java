package com.thread.communication;



/**
 * Shared resource between the producer and consumer
 * 
 * @author maamir
 *
 */
class Resource {
	int num;
	boolean semaphore = false;

	/**
	 * The resource writer
	 * 
	 * @param num
	 */
	public synchronized void resouceWriter(int num) {
		while (semaphore) {
			try {
				wait();
			} catch (Exception e) {
			}
		}
		System.out.println("Write " + num);
		this.num = num;
		semaphore = true;
		notify();
	}

	/**
	 * The Resource Reader
	 */
	public synchronized void resourceReader() {
		while (!semaphore) {
			try {
				wait();
			} catch (Exception e) {
			}
		}
		System.out.println("Read " + num);
		semaphore = false;
		notify();
	}
}

/**
 * The Producer class
 * 
 * @author maamir
 *
 */
class Producer implements Runnable {
	Resource res;

	public Producer(Resource res) {
		this.res = res;
		Thread t = new Thread(this, "Producer");
		t.start();
	}

	public void run() {
		int i = 0;
		while (true) {
			res.resouceWriter(i++);
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}

/**
 * The Consumer class
 * 
 * @author maamir
 *
 */
class Consumer implements Runnable {
	Resource res;

	public Consumer(Resource res) {
		this.res = res;
		Thread t = new Thread(this, "Consumer");
		t.start();
	}

	public void run() {
		while (true) {
			res.resourceReader();
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}

/**
 * The InterCommunication class
 * 
 * InterThread communication in java
 * 
 * @author maamir
 *
 */
public class InterCommunication {
	public static void main(String[] args) {
		Resource q = new Resource();
		new Producer(q);
		new Consumer(q);
	}
}