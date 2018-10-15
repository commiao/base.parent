package base.arch.component.cache.redis.config;

import org.springframework.stereotype.Service;

@Service("redisKeyConfig")
public class RedisKeyConfigImpl implements IKeyConfig {

    /**
     * 获取超时key的前缀
     * @return
     */
    public static String TIMEOUT_PREFIX="TO:";
    /**
     * 获取锁key的前缀
     * @return
     */
    public static String LOCK_PREFIX="LOCK:";
    
    /**
     * 获取超时的key
     * @param key
     * @return  返回的key值是TO:key
     */
    public  String getTimeOutKey(String key){
        return TIMEOUT_PREFIX+key;
    }
   
    /**
     * 获取超时的key
     * @param key
     * @return  返回的key值是LOCK:key
     */
    public  String getLockKey(String key){
        return LOCK_PREFIX+key;
    }

    
    
}
