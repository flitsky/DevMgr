package test.thread;

import java.util.*;

public class Test05 implements Runnable {

	int cnt;

	Test05(int cnt) {
		this.cnt = cnt;
	}

	public void run() {
		try {
			System.out.println(this.cnt + " thread is start");
			Thread.sleep(100);
		} catch (Exception e) {

		}
		System.out.println(this.cnt + "thread is end");
	}

	public static void main(String[] args) {
		ArrayList<Thread> threads = new <Thread>ArrayList();
		for (int i = 1; i <= 10; i++) {
			Thread t = new Thread(new Test05(i));
			t.start();
			threads.add(t);
		}

		for (Thread t : threads) {
			try {
				t.join();
			} catch (Exception e) {

			}
		}
		System.out.println("main is end");
	}

}
