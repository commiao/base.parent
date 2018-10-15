package base.arch.component.cache.redis;

import base.arch.component.cache.redis.exception.CacheException;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @CreateDate: 2018/7/25 13:59
 * @Description: TODO
 * @author: jingmiao
 * @version: V1.0
 */
public interface ICacheService {
    void deleteByKeys(String... var1) throws CacheException;

    void deleteByPrex(String var1) throws CacheException;

    void deleteBySuffix(String var1) throws CacheException;

    Long incrTimeOut(String var1, Long var2, long var3, TimeUnit var5) throws CacheException;

    Long incrNoTimeOut(String var1, Long var2) throws CacheException;

    <V> void saveOrUpdateTimeOut(String var1, V var2, long var3, TimeUnit var5) throws CacheException;

    <V> void saveOrUpdateNoTimeOut(String var1, V var2) throws CacheException;

    <V> V get(String var1, Class<V> var2, boolean var3) throws CacheException;

    <V> List<V> getList(String var1, Class<V> var2, boolean var3) throws CacheException;
}
