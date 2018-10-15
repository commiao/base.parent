package base.arch.component.cache.redis.exception;
/**
 * CreateDate:2016年9月14日下午3:20:50 
 * @Description: 缓存异常  
 * @author:jannal
 * @version V1.0
 */
public class CacheException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public CacheException(String msg) {
		super(msg);
	}

	public CacheException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
