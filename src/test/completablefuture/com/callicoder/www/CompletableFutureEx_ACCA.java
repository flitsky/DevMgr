package test.completablefuture.com.callicoder.www;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.function.Supplier;

public class CompletableFutureEx_ACCA {

	public static void main(String[] args) {
		Supplier<String> RecvReqSignup = () -> {
			System.out.println("A 스레드 작업 시작 : Make Sign-up Request CMD");
			// Thread.sleep(100);
			System.out.println("A 스레드 작업 완료");
			return "A 실행";
		};

		Supplier<String> SendReqSignup = () -> {
			try {
				System.out.println("C 스레드 작업 시작 : Send Sign-up Request CMD");
				System.out.println("C 스레드 작업 대기 : wait resp signup c2d");
				Thread.sleep(500);
				System.out.println("C 스레드 작업 완료 : Receive Sign-up response");
				return "C 실행";
			} catch (InterruptedException e) {
				e.printStackTrace();
				return "실패";
			}
		};

		Future<String> result2 = CompletableFuture.supplyAsync(RecvReqSignup)
				.thenApply(aResult -> aResult + " A 성공 -> ")
				.thenCombine(CompletableFuture.supplyAsync(SendReqSignup), (a, c) -> a + c);

		try {
			System.out.println(result2.get());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
