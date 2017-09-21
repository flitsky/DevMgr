package test.completablefuture.com.callicoder.www;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class CompletableFutureEx {

	public static void main(String[] args) {
		Future<Double> future = CompletableFuture.supplyAsync(() -> {
			try {
				Thread.sleep(2000);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return 1000.0;
		});

		System.out.println("비동기 처리를 하는 동안 다른 일처리.");

		try {
			// 타임아웃 3초로 지정.
			System.out.println("결과 : " + future.get(3000, TimeUnit.MILLISECONDS));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
