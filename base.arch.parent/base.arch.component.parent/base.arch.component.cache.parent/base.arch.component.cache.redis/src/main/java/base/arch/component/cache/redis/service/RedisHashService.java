package base.arch.component.cache.redis.service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @CreateDate: 2018/7/31 16:34
 * @Description: TODO
 * @author: jingmiao
 * @version: V1.0
 */
public interface RedisHashService {

    /**
     * 指定缓存失效时间
     * @author jingmiao
     * @param key 键
     * @param time 时间(秒)
     * @param timeUnit 时间单位
     * @return
     */
    boolean expire(String key, long time, TimeUnit timeUnit);

    boolean put(String key, Object hashKey, Object value, boolean isTimeOutKey);

    boolean put(String key, Object hashKey, Object value, long time, TimeUnit timeUnit);

    boolean put(String key, Map<String, String> map);

    boolean put(String key, Map<String, String> map, long time, TimeUnit timeUnit);

    String get(String key, Object hashKey, boolean isTimeOutKey);

    Map<Object,Object> get(String key, boolean isTimeOutKey);

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
