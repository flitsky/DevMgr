package test.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExchangerTest {
	Exchanger<List<String>> exchanger = new Exchanger<List<String>>();
	List<String> exchnagerList = new ArrayList<String>();

	class AddList implements Runnable {
		public void run() {
			try {
				while (true) {
					exchnagerList.add("1");
					if (exchnagerList.size() == 1) {
						exchnagerList = exchanger.exchange(exchnagerList);

					}
				}
			} catch (InterruptedException ex) {
				System.out.println(ex);
			}
		}
	}

	class SubtractList implements Runnable {
		public void run() {
			try {
				while (true) {
					exchnagerList.remove("1");
					if (exchnagerList.size() == 0) {
						exchnagerList = exchanger.exchange(exchnagerList);

					}
				}
			} catch (InterruptedException ex) {
				System.out.println(ex);
			}
		}
	}

	public static void main(String... args) {
		final ExecutorService exService = Executors.newFixedThreadPool(2);
		ExchangerTest ob = new ExchangerTest();
		exService.execute(ob.new SubtractList());
		exService.execute(ob.new AddList());
	}
}