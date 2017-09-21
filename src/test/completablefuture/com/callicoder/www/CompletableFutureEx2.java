package test.completablefuture.com.callicoder.www;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class CompletableFutureEx2 {

	public static void main(String[] args) {
		Supplier<Double> supplier = () -> {
			try {
				Thread.sleep(2000);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return 1000.0;
		};

		List<Supplier<Double>> supplierList = Arrays.asList(supplier, supplier, supplier, supplier);

		// 병렬 스트림을 이용. 각 태스크를 병렬로 하여 성능을 높이자.
		supplierList.parallelStream().map(Supplier::get).reduce(Double::sum).ifPresent(System.out::println);

		// CompletableFuture 를 이용한 비동기적으로 처리
		{
			List<CompletableFuture<Double>> completableFutures = supplierList.stream()
					.map(CompletableFuture::supplyAsync).collect(Collectors.toList());

			// join 메소드는 모든 비동기 동작이 끝나길 기다립니다.
			completableFutures.stream().map(CompletableFuture::join).reduce(Double::sum).ifPresent(System.out::println);
		}
	}

}
