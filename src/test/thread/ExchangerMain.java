package test.thread;

import java.util.concurrent.Exchanger;
import java.util.concurrent.atomic.AtomicReference;

public class ExchangerMain {

	public static void main(String[] args) {
		final Exchanger<Integer> e = new Exchanger<Integer>();
		new Thread(new Runnable() {

			private final AtomicReference<Integer> last = new AtomicReference<Integer>(1);

			@Override
			public void run() {
				try {
					while (true) {
						last.set(e.exchange(last.get()));
						System.out.println("Thread A has value: " + last.get());
						Thread.sleep(2000);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}).start();
		new Thread(new Runnable() {

			private final AtomicReference<Integer> last = new AtomicReference<Integer>(2);

			@Override
			public void run() {
				try {
					while (true) {
						last.set(e.exchange(last.get()));
						System.out.println("Thread B has value: " + last.get());
						Thread.sleep(2000);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}).start();
	}

}