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

    boolean expire(String key,long time,TimeUnit timeUnit);
}
