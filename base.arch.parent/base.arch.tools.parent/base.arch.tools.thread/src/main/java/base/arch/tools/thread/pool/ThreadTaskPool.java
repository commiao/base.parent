package base.arch.tools.thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * CreateDate:2016年10月26日下午12:25:45
 * 
 * @Description: 线程池
 * @author:hehch
 * @version V1.0
 */
public class ThreadTaskPool {

	private final ExecutorService cachedThreadPool;
	public static void main(String[] args) {
		System.out.println(Runtime.getRuntime().availableProcessors());
	}
	private ThreadTaskPool() {
		
		this.cachedThreadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2 + 1<8?8:Runtime.getRuntime().availableProcessors() * 2 + 1,
				new ThreadFactory() {
					AtomicInteger atomic = new AtomicInteger();

					public Thread newThread(Runnable r) {
						return new Thread(r, "PofInterfaceThread-" + this.atomic.getAndIncrement());
					}
				});
		// new ThreadPoolExecutor(0, Runtime.getRuntime().availableProcessors()
		// * 2 + 1, 0L,
		// TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
	}

	private static class ThreadTaskPoolInner {
		public static ThreadTaskPool pool = new ThreadTaskPool();
	}

	public static ThreadTaskPool getInstance() {
		return ThreadTaskPoolInner.pool;
	}

	/**
	 * 获取 线程池
	 * 
	 * @return cachedThreadPool
	 */
	public ExecutorService getCachedThreadPool() {
		return cachedThreadPool;
	}
}
