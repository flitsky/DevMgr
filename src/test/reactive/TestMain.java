package test.reactive;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Observable;
import java.util.concurrent.TimeUnit;

public class TestMain {

	private static boolean isSlowTickTime() {
		return LocalDate.now().getDayOfWeek() == DayOfWeek.SATURDAY ||
				LocalDate.now().getDayOfWeek() == DayOfWeek.SUNDAY;
	}
	
	private static long start = System.currentTimeMillis();
	public static Boolean isSlowTime() {
		return (System.currentTimeMillis() - start) % 30_000 >= 15_000;
	}
	
	public static void main(String[] args) {
		
		//Observable<Long> fast = Observable.interval(1, TimeUnit.SECONDS);
		
	}
}
