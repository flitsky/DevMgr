package test.code;

import java.time.Duration;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class CompletableFuture_pollingtask {

	public static <T> CompletableFuture<T> poll(final Supplier<Optional<T>> pollingTask, final Duration frequency,
			final ScheduledExecutorService executorService) {
		final CompletableFuture<T> result = new CompletableFuture<>();
		final ScheduledFuture<?> scheduled = executorService.scheduleAtFixedRate(() -> pollTask(pollingTask, result), 0,
				frequency.toMillis(), TimeUnit.MILLISECONDS);
		result.whenComplete((r, ex) -> scheduled.cancel(true));
		return result;
	}

	private static <T> void pollTask(final Supplier<Optional<T>> pollingTask, final CompletableFuture<T> resultFuture) {
		try {
			pollingTask.get().ifPresent(resultFuture::complete);
		} catch (Exception ex) {
			resultFuture.completeExceptionally(ex);
		}
	}
	
}
