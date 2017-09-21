package test.completablefuture.com.callicoder.www;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.function.Supplier;

public class CompletableFutureEx_AABB {

	public static void main(String[] args) {
		Supplier<String> A = () -> {
			try {
				System.out.println("A 스레드 작업 시작");
				Thread.sleep(2000);
				System.out.println("A 스레드 작업 완료");
				return "A 실행";
			} catch (InterruptedException e) {
				e.printStackTrace();
				return "실패";
			}
		};

		Function<String, String> B = (aResult) -> {
			try {
				System.out.println("B 스레드 작업 시작");
				Thread.sleep(1000);
				System.out.println("B 스레드 작업 완료");
				return aResult + " B 실행";
			} catch (InterruptedException e) {
				e.printStackTrace();
				return "실패";
			}
		};

		Future<String> result = CompletableFuture.supplyAsync(A).thenApply(aResult -> aResult + " A 성공 -> ")
				.thenCompose(aSucceedResult -> CompletableFuture.supplyAsync(() -> B.apply(aSucceedResult)));

		try {
			System.out.println(result.get());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
