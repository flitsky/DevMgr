package test.completablefuture.com.callicoder.www;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletableFuture_thenAccept {

	public static void main(String[] args) {
		// thenAccept() example
		CompletableFuture.supplyAsync(() -> {
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return 1;// ProductService.getProductDetail(productId);
		}).thenAccept(product -> {
			System.out.println("Got product detail from remote service " + " kng "/* product.getName() */);
		});
	}
}
