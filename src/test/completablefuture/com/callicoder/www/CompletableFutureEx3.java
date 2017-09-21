package test.completablefuture.com.callicoder.www;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CompletableFutureEx3 {

	public static void main(String[] args) {
		// 에러 고쳐서 돌려야함...
		//List<Supplier<Double>> supplierList = IntStream.range(0, 100).mapToObj(n -> supplier).collect(Collectors.toList());
		List<Supplier<Double>> supplierList = null;//IntStream.range(0, 100).mapToObj(n -> supplier).collect(Collectors.toList());
		 
		supplierList.parallelStream().
		        map(Supplier::get).
		        reduce(Double::sum).
		        ifPresent(System.out::println);
		 
		// CompletableFuture 를 이용한 비동기적으로 처리
		{
		 
		    final Executor executor = Executors.newFixedThreadPool(Math.min(supplierList.size(), 100), r -> {
		                Thread t = new Thread(r);
		                // 데몬 스레드 정의
		                // 일반 스레드가 실행 중일 때 자바 프로그램은 종료되지 않음 -> 어떤 이벤트를 한없이 기다리면서 종료되지 않은 일반 자바 스레드가 있으면 문제
		                // 데몬 스레드는 자바 프로그램이 종료될 때 종료
		                t.setDaemon(true);
		                return t;
		            });
		 
		    List<CompletableFuture<Double>> completableFutures = supplierList.stream().
		            map(CompletableFuture::supplyAsync).
		            collect(Collectors.toList());
		 
		    // join 메소드는 모든 비동기 동작이 끝나길 기다립니다.
		    completableFutures.stream().
		            map(CompletableFuture::join).
		            reduce(Double::sum).
		            ifPresent(System.out::println);
		}
	}
}
