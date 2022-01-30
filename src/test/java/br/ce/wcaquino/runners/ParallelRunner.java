package br.ce.wcaquino.runners;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerScheduler;

// Então esse aqui é nosso Runner Paralelo
public class ParallelRunner extends BlockJUnit4ClassRunner {

	public ParallelRunner(Class<?> klass) throws InitializationError {
		super(klass);
		setScheduler(new ThreadPoll());
	}

	private static class ThreadPoll implements RunnerScheduler {

		private ExecutorService executor;
		
		public ThreadPoll() {
			executor = Executors.newFixedThreadPool(2); // aqui é o número de threads que eu quero que ele execute. O Java é quem vai gerenciar, mas interessante não aumentar tanto
		}

		@Override
		public void schedule(Runnable run) {
			executor.submit(run);
		}

		@Override
		public void finished() {
			executor.shutdown();
			try {
				executor.awaitTermination(10, TimeUnit.MINUTES);
			} catch (InterruptedException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		
	}
}
