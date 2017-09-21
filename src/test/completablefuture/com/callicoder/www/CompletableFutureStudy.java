package test.completablefuture.com.callicoder.www;

import java.util.Iterator;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.lang.System.out;
import static java.lang.Thread.currentThread;

public class CompletableFutureStudy {

	public static void main(String[] args) throws InterruptedException {
		ExecutorService executorService = Executors.newFixedThreadPool(4, new ThreadFactory() {
			final AtomicInteger threadCounter = new AtomicInteger(0);

			@Override
			public Thread newThread(Runnable runnable) {
				return new Thread(runnable, "loop-" + threadCounter.getAndIncrement());
			}
		});

		RemoteService remoteService = new RemoteService();
		Iterator<UUID> iterator = Stream.generate(UUID::randomUUID).limit((int) (Math.random() * 10 + 5)).iterator();

		CompletableFuture<Void> completableFuture = loop(() -> CompletableFuture.supplyAsync(() -> {
			out.println(currentThread().getName() + " : iterator.next");

			return iterator.next();
		}, executorService).whenCompleteAsync((uuid, throwable) -> {
			out.println(currentThread().getName() + " : remoteService.push");

			try {
				if (Objects.nonNull(throwable)) {
					throw throwable;
				}

				remoteService.push(uuid);
			} catch (Throwable error) {
				throw new RuntimeException(error);
			}
		}, executorService), executorService);

		completableFuture.whenCompleteAsync((aVoid, throwable) -> {
			out.println(currentThread().getName() + " : complete");
		}, executorService);

		// completableFuture.cancel(true) 작업취소

		executorService.awaitTermination(10, TimeUnit.SECONDS);
		executorService.shutdown();
	}

	static CompletableFuture<Void> loop(Supplier<CompletableFuture<?>> supplier, Executor executor) {
		return loop(new CompletableFuture<>(), supplier, executor);
	}

	static CompletableFuture<Void> loop(CompletableFuture<Void> promise, Supplier<CompletableFuture<?>> supplier,
			Executor executor) {
		supplier.get().exceptionally(throwable -> {
			out.println(currentThread().getName() + " : error - " + throwable.getMessage());

			promise.completeExceptionally(new CancellationException(throwable.getMessage()));
			return null;
		}).thenRunAsync(() -> {
			if (!promise.isCancelled()) {
				out.println(currentThread().getName() + " : loop");

				loop(promise, supplier, executor);
			}
		}, executor);

		return promise;
	}

	static class RemoteService {
		void push(UUID uuid) throws InterruptedException {
			Thread.sleep(1000);
		}
	}

}