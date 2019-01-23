package base.arch.component.cache.redis.service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @CreateDate: 2018/7/31 14:09
 * @Description: TODO
 * @author: jingmiao
 * @version: V1.0
 */
public interface RedisService {

    boolean put(final String key, final String value);

    boolean put(final String key, final String value, long time, TimeUnit timeUnit);

    String get(String key, boolean isTimeOutKey);

    Set<String> keys(String pattern);

    boolean expire(String key, long time, TimeUnit timeUnit);

    /**
     * 将key添加后缀时间戳
     * @param key
     * @return
     */
    String getTimeKeyCache(String key);

    /**
     * 根据后缀查找最新的key，并将旧key设置失效时间
     * @param prex 去除时间戳"-yyyyMMddHHmmss"之后的key
     * @param isDel 是否将旧的key设置缓存失效时间 默认1分钟后失效
     * @return
     */
    String getNewKeyCache(String prex, boolean isDel);
}
