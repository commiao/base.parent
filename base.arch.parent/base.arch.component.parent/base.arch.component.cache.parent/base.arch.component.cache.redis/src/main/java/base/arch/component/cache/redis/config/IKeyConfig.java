package base.arch.component.cache.redis.config;


/**
 * @author jannal
 */
public interface IKeyConfig   {
    
    /**
     * 获取超时的key
     * @param key
     * @return 
     */
    public  String getTimeOutKey(String key);
   
    /**
     * 获取锁的key
     * @param key
     * @return 
     */
    public  String getLockKey(String key);
}
