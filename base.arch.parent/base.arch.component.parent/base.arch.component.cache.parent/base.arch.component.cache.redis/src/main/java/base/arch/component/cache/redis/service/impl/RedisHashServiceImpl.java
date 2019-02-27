package base.arch.component.cache.redis.service.impl;

import base.arch.component.cache.redis.service.RedisHashService;
import base.arch.component.cache.redis.templ.RedisTempl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @CreateDate: 2018/7/31 16:34
 * @Description: TODO
 * @author: jingmiao
 * @version: V1.0
 */
@Service
public class RedisHashServiceImpl extends RedisTempl<String,String> implements RedisHashService{

    @Override
    public boolean put(final String key, final Object hashKey, final Object value, boolean isTimeOutKey){
        try {
            this.redisTemplate.opsForHash().put(getTimeOutKey(key,isTimeOutKey),hashKey,value);
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean put(final String key, final Object hashKey, final Object value, final long time, final TimeUnit timeUnit){
        String timeKey = getTimeOutKey(key,true);
        try {
            this.redisTemplate.opsForHash().put(timeKey, hashKey, value);
            expire(timeKey,time,timeUnit);
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean put(final String key,final Map<String,String> map){
        try {
            this.redisTemplate.opsForHash().putAll(getTimeOutKey(key,false), map);
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean put(final String key, final Map<String, String> map, final long time, final TimeUnit timeUnit){
        String timeKey = getTimeOutKey(key,true);
        try {
            this.redisTemplate.opsForHash().putAll(timeKey, map);
            expire(timeKey,time,timeUnit);
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public String get(final String key, final Object hashKey, boolean isTimeOutKey){
        String jsonResult = null;
        try {
            jsonResult = (String) this.redisTemplate.opsForHash().get(getTimeOutKey(key,isTimeOutKey), hashKey);
        } catch (Exception e){
            e.printStackTrace();
            return jsonResult;
        }
        return jsonResult;
    }

    @Override
    public Map<Object,Object> get(final String key, boolean isTimeOutKey){
        Map<Object,Object> jsonResult = new HashMap<>();
        try {
            jsonResult = this.redisTemplate.opsForHash().entries(getTimeOutKey(key,isTimeOutKey));
        } catch (Exception e){
            e.printStackTrace();
            return jsonResult;
        }
        return jsonResult;
    }
}
