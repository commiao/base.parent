package base.arch.tools.thread.pool;

import base.arch.tools.thread.ExecutorServiceFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * CreateDate:2016年10月26日下午12:25:45
 * 
 * @Description: 线程池
 * @author:hehch
 * @version V1.0
 */
public class ThreadTaskPool {

	private final ExecutorService executor;
	private static ThreadTaskPool pool = new ThreadTaskPool();
	private final int threadMax = Runtime.getRuntime().availableProcessors() * 2 + 1<8?8:Runtime.getRuntime().availableProcessors() * 2 + 1;

	private ThreadTaskPool() {
		this.executor = ExecutorServiceFactory.getInstance().createFixedThreadPool(threadMax);
	}

	public static ThreadTaskPool getInstance() {
		return pool;
	}

	/**
	 * 获取 线程池
	 *
	 * @return cachedThreadPool
	 */
	public ExecutorService getCachedThreadPool() {
		return executor;
	}

	/**
	 * 关闭线程池，这里要说明的是：调用关闭线程池方法后，线程池会执行完队列中的所有任务才退出
	 *
	 */
	public void shutdown(){
		executor.shutdown();
	}

	/**
	 * 提交任务到线程池，可以接收线程返回值
	 *
	 * @param task
	 * @return
	 */
	public Future<?> submit(Runnable task) {
		return executor.submit(task);
	}

	/**
	 * 提交任务到线程池，可以接收线程返回值
	 *
	 * @param task
	 * @return
	 */
	public Future<?> submit(Callable<?> task) {
		return executor.submit(task);
	}

	/**
	 * 直接提交任务到线程池，无返回值
	 *
	 * @param task
	 * @author SHANHY
	 * @date   2015年12月4日
	 */
	public void execute(Runnable task){
		executor.execute(task);
	}

	public static void main(String[] args) {
		System.out.println(Runtime.getRuntime().availableProcessors());
	}
}
