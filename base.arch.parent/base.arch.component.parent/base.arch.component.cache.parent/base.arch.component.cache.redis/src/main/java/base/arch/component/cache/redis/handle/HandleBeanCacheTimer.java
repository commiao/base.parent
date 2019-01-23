package base.arch.component.cache.redis.handle;

import base.arch.component.cache.redis.constant.CacheConstant;
import base.arch.component.cache.redis.service.RedisService;
import base.arch.component.cache.redis.util.JsonOrgUtils;
import base.arch.component.cache.redis.util.JsonUtils;
import base.arch.component.cache.redis.util.ObjectTranscoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @CreateDate: 2018/7/30 14:08
 * @Description: 时间缓存控制
 * @author: jingmiao
 * @version: V1.0
 */
@Component
public class HandleBeanCacheTimer {
    private static Logger logger = LoggerFactory.getLogger(HandleBeanCacheTimer.class);

    @Autowired
    private RedisService redisService;

    private static RedisService redisServiceStatic;

    @PostConstruct
    public void init() {
        redisServiceStatic = redisService;
    }

    public static String getNewKeyCache(String key, boolean isDel){
        return redisServiceStatic.getNewKeyCache(key, isDel);
    }

    public static boolean setObjectForTwoHours(String key, Object obj){
        return setObjectCache(key, obj, CacheConstant.PofInterfaceCacheConfigConstant.POF_INTERFACE_CACHE_LIVE_TIME_TWO_HOURS, TimeUnit.MINUTES);
    }
    public static boolean setObjectForOneDay(String key, Object obj){
        return setObjectCache(key, obj, CacheConstant.PofInterfaceCacheConfigConstant.POF_INTERFACE_CACHE_LIVE_TIME_ONE_DAY, TimeUnit.HOURS);
    }

    /**
     * json不转换Date类型格式
     * @param key
     * @param obj
     * @param timeOut
     * @param timeUnit
     * @return
     */
    public static boolean setObjectCache(String key, Object obj, long timeOut, TimeUnit timeUnit){
        return setObjectCache(key, obj, null, timeOut, timeUnit);
    }

    /**
     * json转换Date格式类型
     * @param key
     * @param obj
     * @param dateFormat "yyyy-MM-dd HH:mm:ss.SSS"
     * @param timeOut
     * @param timeUnit
     * @return
     */
    public static boolean setObjectCache(String key, Object obj, String dateFormat, long timeOut, TimeUnit timeUnit){
        StringBuffer logStr = new StringBuffer();
        String time_key = redisServiceStatic.getTimeKeyCache(key);
        logStr.append("向缓存中【存储】时间戳对象：").append(time_key);
        boolean bol = false;
        try {
            String json = JsonUtils.bean2Json(obj, dateFormat);
            bol = redisServiceStatic.put(time_key, json, timeOut, timeUnit);
        } catch (Exception e) {
            logStr.append("信息【出现异常】");
//			redisCacheServiceStatic.deleteByPrex(key);
            logger.error(logStr.toString(), e);
            bol = false;
        }
        if(bol){
            redisServiceStatic.getNewKeyCache(key,true);
        }
        return bol;
    }

    public static <V> V getObjectCache(String key,Class<V> clazz){
        return getObjectCache(key, clazz,0);
    }

    public static <V> V getObjectCache(String key,Class<V> clazz, int jsonParseType){
        String new_key = redisServiceStatic.getNewKeyCache(key, true);
        String json = null;
        V v = null;
        // getNewKeyCache方法已处理TO:问题，所以传false
        try {
            json = redisServiceStatic.get(new_key, false);
        } catch (Exception e) {
            logger.debug("从缓存中查询key：{}-查询失败",new_key);
            return v;
        }
        try {
            if(jsonParseType==1){
                v = JsonOrgUtils.fromJson(json, clazz);
            }else {
                v = JsonUtils.json2ObjStrong(json, clazz);
            }
        } catch (Exception e) {
//			redisCacheServiceStatic.deleteByPrex(key);
            logger.debug("从缓存中查询key：{}-解析失败",new_key);
        }
        return v;
    }

    public static <V> V getObjectCache(String key,Class<V> clazz,Map<String, Class<?>> classMap){
        String new_key = redisServiceStatic.getNewKeyCache(key, true);
        String json = null;
        V v = null;
        // getNewKeyCache方法已处理TO:问题，所以传false
        try {
            json = redisServiceStatic.get(new_key, false);
        } catch (Exception e) {
            logger.debug("从缓存中查询key：{}-查询失败",new_key);
            return v;
        }
        try {
            v = JsonUtils.json2Obj(json,clazz,classMap);
        } catch (Exception e) {
//			redisCacheServiceStatic.deleteByPrex(key);
            logger.debug("从缓存中查询key：{}-解析失败",new_key);
        }
        return v;
    }

    public static <V> Map<String,V> getMap(String key){
        String new_key = redisServiceStatic.getNewKeyCache(key, true);
        String serializ = null;
        Map<String, V> map = null;
        // getNewKeyCache方法已处理TO:问题，所以传false
        try {
            serializ = redisServiceStatic.get(new_key, false);
        } catch (Exception e) {
            logger.debug("从缓存中查询key：{}-查询失败",new_key);
            return map;
        }
        try {
            map = (Map<String, V>) ObjectTranscoder.FromString(serializ);
        } catch (Exception e) {
//			redisCacheServiceStatic.deleteByPrex(key);
            logger.debug("从缓存中查询key：{}-解析失败",new_key);
        }
        return map;
    }

    public static <V> boolean setMap(String key,Map<String,V> map,long timeOut,TimeUnit timeUnit){
        StringBuffer logStr = new StringBuffer();
        String time_key = redisServiceStatic.getTimeKeyCache(key);
        logStr.append("向缓存中【存储】时间戳对象：").append(time_key);
        boolean bol = false;
        try {
            String ser = ObjectTranscoder.ToString(map);
            bol = redisServiceStatic.put(time_key, ser, timeOut, timeUnit);
        } catch (Exception e) {
            logStr.append("信息【出现异常】");
//			redisCacheServiceStatic.deleteByPrex(key);
            logger.error(logStr.toString(), e);
            bol = false;
        }
        if(bol){
            redisServiceStatic.getNewKeyCache(key,true);
        }
        return bol;
    }

}
