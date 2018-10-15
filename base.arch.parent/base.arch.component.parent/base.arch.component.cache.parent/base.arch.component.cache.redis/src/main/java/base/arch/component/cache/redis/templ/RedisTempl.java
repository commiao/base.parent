package base.arch.component.cache.redis.templ;

import base.arch.component.cache.redis.exception.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @CreateDate: 2018/7/31 12:13
 * @Description: TODO
 * @author: jingmiao
 * @version: V1.0
 */
public abstract class RedisTempl<K extends Serializable,V extends Serializable> {
    @Autowired
    protected RedisTemplate<K,V> redisTemplate;

    public RedisSerializer<String> getRedisSerializer() {
        return redisTemplate.getStringSerializer();
    }

    public void setRedisTemplate(RedisTemplate<K, V> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * @Description::指定永久缓存. <br/>
     * @author jingmiao
     * @param key
     * @return
     */
    public boolean persist(K key){
        try {
            redisTemplate.persist(key);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 指定缓存失效时间
     * @author jingmiao
     * @param key 键
     * @param time 时间(秒)
     * @param timeUnit 时间单位
     * @return
     */
    public boolean expire(K key,long time,TimeUnit timeUnit){
        try {
            if(time>0){
                redisTemplate.expire(key, time, timeUnit);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void deleteByKeys(K... keys) throws CacheException {
        if(!StringUtils.isEmpty(keys) && keys.length != 0) {
            if(keys.length == 1) {
                if(StringUtils.isEmpty(keys[0])) {
                    throw new IllegalArgumentException("指定删除的key不能为空");
                }

                this.redisTemplate.delete(keys[0]);
            } else {
                this.redisTemplate.delete(Arrays.asList(keys));
            }

        } else {
            throw new IllegalArgumentException("指定删除的key不能为空");
        }
    }

    public Set<K> getKeys(K pattern) {
        return redisTemplate.keys(pattern) ;
    }

    public String getTimeOutKey(String key, boolean isTimeOutKey) {
        if(isTimeOutKey) {
            key = "TO:"+key;
        }
        return key;
    }

}
