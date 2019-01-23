package base.arch.component.cache.redis.service.impl;

import base.arch.component.cache.redis.exception.CacheException;
import base.arch.component.cache.redis.service.RedisService;
import base.arch.component.cache.redis.templ.RedisTempl;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @CreateDate: 2018/7/31 12:08
 * @Description: redis操作String类型数据
 * @author: jingmiao
 * @version: V1.0
 */
@Service
public class RedisServiceImpl extends RedisTempl<String,String> implements RedisService {

    @Override
    public boolean put(final String key, final String value) {
        try {
            redisTemplate.opsForValue().set(getTimeOutKey(key,false),value);
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean put(final String key, final String value, long time, TimeUnit timeUnit) {
        try {
            redisTemplate.opsForValue().set(getTimeOutKey(key,true), value, time, timeUnit);
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public String get(final String key, boolean isTimeOutKey){
        String result = null;
        try{
            result = redisTemplate.opsForValue().get(getTimeOutKey(key,isTimeOutKey));
        } catch (Exception e) {
            throw new CacheException(key + "获取值发生错误,具体原因如下：" + e.getMessage(), e);
        }
        return result;
    }

    /**
     * @Description::查询主键set集合. <br/>
     * *：通配任意多个字符.<br/>
     * ?：通配单个字符.<br/>
     * []：通配括号内的某一个字符.<br/>
     * @author jingmiao
     * @param pattern
     * @return
     */
    @Override
    public Set<String> keys(String pattern){
        return redisTemplate.keys(pattern);
    }

}
