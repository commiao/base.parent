package base.arch.component.cache.redis.service;

import java.util.concurrent.TimeUnit;

import base.arch.component.cache.redis.ICacheService;
import base.arch.component.cache.redis.exception.CacheException;

/**
 * @CreateDate: 2018/7/25 13:57
 * @Description: TODO
 * @author: jingmiao
 * @version: V1.0
 */
public interface RedisForStringService extends ICacheService{
    /**
     * 返回列表的长度
     * 
     * @param key
     * @param value
     * @param liveTime
     * @param isSetTimeOut
     * @param timeUnit
     * @return
     * @throws CacheException
     */
    public <V> Long rpush(String key, V value, long liveTime, boolean isSetTimeOut, TimeUnit timeUnit) throws CacheException;

    public <V> void hashSetSaveOrUpdateTimeOut(String key, V value, long liveTime, boolean isSetTimeOut, TimeUnit timeUnit) throws CacheException;

    public <V> void hashSetSaveOrUpdateNoTimeOut(String key, V value) throws CacheException;

    public <V> V hashSetGet(String key,Class<V> clazz,boolean isTimeOutKey);
}
