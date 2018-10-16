package base.arch.component.logger.util.concurrent;
/**
 * CreateDate:2016年11月7日下午3:37:41 
 * @Description: 审计日志线程Context
 * @author:jannal
 * @version V1.0
 */
public class LoggerThreadContext {
	
	private final static ThreadLocal<LoggerInfo> threadLocal=new ThreadLocal<LoggerInfo>(){
		@Override
		protected LoggerInfo initialValue() {
			return new LoggerInfo();
		}
	};
	
	public static void setAuditInfo(LoggerInfo value){
		threadLocal.set(value);
	}
	
	public static LoggerInfo get(){
	   return  threadLocal.get();
	}
	
	public static void clear(){
		threadLocal.remove();
	}
}
