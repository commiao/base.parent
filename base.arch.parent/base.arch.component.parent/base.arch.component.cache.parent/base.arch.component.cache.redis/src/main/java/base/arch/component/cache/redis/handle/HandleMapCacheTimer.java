package base.arch.component.cache.redis.handle;

import base.arch.component.cache.redis.service.RedisHashService;
import base.arch.component.cache.redis.util.JsonOrgUtils;
import base.arch.component.cache.redis.util.JsonUtils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @CreateDate: 2018/7/30 14:08
 * @Description: 时间缓存控制
 * @author: jingmiao
 * @version: V1.0
 */
@Component
public class HandleMapCacheTimer {

    @Autowired
    private RedisHashService redisHashService;

    private static RedisHashService redisHashServiceStatic;

    private static Logger logger = LoggerFactory.getLogger(HandleMapCacheTimer.class);

    @PostConstruct
    public void init() {
        redisHashServiceStatic = redisHashService;
    }

    public static <V> V getHashBean(String key, String hashKey, Class<V> clazz, boolean isTimeOutKey){
        String json;
        V v = null;
        try {
            json = redisHashServiceStatic.get(key,hashKey,isTimeOutKey);
        } catch (Exception e) {
            logger.debug("从缓存中查询key：{}-hsahKey：{}-查询失败",key,hashKey);
            return v;
        }
        try {
            v = JsonUtils.json2Obj(json, clazz);
        } catch (Exception e) {
            logger.debug("从缓存中查询key：{}-hsahKey：{}-查询失败",key,hashKey);
        }
        return v;
    }

    public static <V> List<V> getHashList(String key, String hashKey, Class<V> clazz, boolean isTimeOutKey){
        String json;
        List<V> result = null;
        try {
            json = redisHashServiceStatic.get(key,hashKey,isTimeOutKey);
            if(json!=null) {
                List<String> listJson = JsonUtils.json2ArrFast(json, String.class);
                result = JsonUtils.json2List(listJson, clazz);
            }
        } catch (Exception e) {
            logger.debug("从缓存中查询key：{}-hsahKey：{}-查询失败",key,hashKey);
        }
        return result;
    }

    public static <V>boolean setHashMap(String key, Map<String,V> map, long timeOut, TimeUnit timeUnit){
        return setHashMap(key,map,null,timeOut,timeUnit);
    }

    public static <V>boolean setHashMap(String key, Map<String,V> map, String dateFormat, long timeOut, TimeUnit timeUnit){
        Map<String,String> result = JsonUtils.map2Json(map,dateFormat);
        String time_key = redisHashServiceStatic.getTimeKeyCache(key);
        boolean isSuc = redisHashServiceStatic.put(time_key,result,timeOut,timeUnit);
        if(isSuc){
            redisHashServiceStatic.getNewKeyCache(key, true);
        }
        return isSuc;
    }

    public static <V> Map<String,V> getHashMap(String key, Class<V> clazz){
        // time_key 已处理TO前缀问题
        Map<Object,Object> map = getHashMap(key);
        Map<String, V> result = null;
        if(map!=null) {
            result = JsonUtils.json2Map(map, clazz);
        }
        return result;
    }

    public static Map<Object,Object> getHashMap(String key){
        String time_key = redisHashServiceStatic.getNewKeyCache(key, true);
        Map<Object,Object> map = null;
        if(StringUtils.isNotBlank(time_key)) {
            // time_key 已处理TO前缀问题
            map = redisHashServiceStatic.get(time_key, false);
        }
        return map;
    }

    public static <V> V getHashBean(String key,String hashKey, Class<V> clazz){
        return getHashBean(key,hashKey,clazz,0);
    }
    public static <V> V getHashBean(String key,String hashKey, Class<V> clazz, int jsonParseType){
        String time_key = redisHashServiceStatic.getNewKeyCache(key, true);
        // time_key 已处理TO前缀问题
        String result = redisHashServiceStatic.get(time_key,hashKey,false);
        V v = null;
        try {
            if(jsonParseType==1){
                v = JsonOrgUtils.fromJson(result, clazz);
            }else {
                v = JsonUtils.json2ObjStrong(result, clazz);
            }
        } catch (Exception e) {
            logger.error("从缓存中查询key：{},hashKe：{}-解析失败，错误信息：{}",key,hashKey,e.getMessage());
        }

        return v;
    }

    public static boolean setHashBean(String key, String hashKey, Object value, String dateFormat){
        String time_key = redisHashServiceStatic.getNewKeyCache(key, true);
        // time_key 已处理TO前缀问题
        return redisHashServiceStatic.put(time_key, hashKey, JsonUtils.bean2Json(value,dateFormat), false);
    }

    public static boolean setHashTimeOutCache(String key, long timeOut, TimeUnit timeUnit){
        return redisHashServiceStatic.expire(key, timeOut, timeUnit);
    }

    public static boolean setHashCache(String key, Object hashKey, Object value, long timeOut, TimeUnit timeUnit){
        return redisHashServiceStatic.put(key, hashKey, value, timeOut,timeUnit);
    }

    public static boolean setHashCache(String key, String hashKey, Object value, boolean isTimeOutKey){
        return redisHashServiceStatic.put(key, hashKey, value, isTimeOutKey);
    }
    public static <T>boolean setHashList(String key, String hashKey, List<T> list, boolean isTimeOutKey){
        return redisHashServiceStatic.put(key, hashKey, JSON.toJSONString(JSON.toJSON(list)), isTimeOutKey);
    }
}
