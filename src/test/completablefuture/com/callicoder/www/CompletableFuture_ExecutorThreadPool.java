package test.completablefuture.com.callicoder.www;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CompletableFuture_ExecutorThreadPool {

	public static void main(String[] args) {
		Executor executor = Executors.newFixedThreadPool(10);
		CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
		    try {
		        TimeUnit.SECONDS.sleep(2);
		    } catch (InterruptedException e) {
		        e.printStackTrace();
		    }
		    return "Result of the asynchronous computation";
		}, executor);
		
		try {
			String result;
			for (int i =0 ; i<20 ; i++) {
				result = future.get();
				System.out.println(result);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
