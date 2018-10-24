package base.arch.tools.thread;

import java.util.concurrent.Callable;

/**
 * CreateDate:2016年11月14日上午9:45:39
 * 
 * @Description: 线程任务基类
 * @author:hehch
 * @version V1.0
 */
public abstract class AbstractThreadCallable<T> implements Callable<T> {
	
	private long overTime;

	private long threadTimeOut = 30000;

	public AbstractThreadCallable() {
		this.overTime = threadTimeOut + System.currentTimeMillis();
	}

	@Override
	public T call() throws Exception {
		if (this.overTime < System.currentTimeMillis()) {
			throw new Exception("===========================异步线程服务调用超时！=============================");
		}
		return internalCall();
	}

	public abstract T internalCall() throws Exception;
}
