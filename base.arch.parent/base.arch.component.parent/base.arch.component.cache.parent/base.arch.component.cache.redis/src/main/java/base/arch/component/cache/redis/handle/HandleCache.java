package base.arch.component.cache.redis.handle;

import base.arch.component.cache.redis.constant.CacheConstant;
import base.arch.component.cache.redis.service.RedisService;
import base.arch.component.cache.redis.util.JsonUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @CreateDate: 2018/8/1 2:13
 * @Description: TODO
 * @author: jingmiao
 * @version: V1.0
 */
@Component
public class HandleCache {
    @Autowired
    private RedisService redisService;

    private static RedisService redisServiceStatic;

    private static Logger logger = LoggerFactory.getLogger(HandleCache.class);

    @PostConstruct
    public void init() {
        redisServiceStatic = redisService;
    }

    public static boolean setObjectForTwoHours(String key, Object obj){
        return setObjectCache(key, obj, CacheConstant.PofInterfaceCacheConfigConstant.POF_INTERFACE_CACHE_LIVE_TIME_TWO_HOURS, TimeUnit.MINUTES);
    }
    public static boolean setObjectForOneDay(String key, Object obj){
        return setObjectCache(key, obj, CacheConstant.PofInterfaceCacheConfigConstant.POF_INTERFACE_CACHE_LIVE_TIME_ONE_DAY, TimeUnit.HOURS);
    }

    private static boolean setObjectCache(String key, Object obj, long timeOut, TimeUnit timeUnit){
        StringBuffer logStr = new StringBuffer();
        logStr.append("向缓存中【存储】对象：").append(key);
        boolean bol = false;
        try {
            String json = JsonUtils.bean2Json(obj);
            bol = redisServiceStatic.put(key, json, timeOut, timeUnit);
        } catch (Exception e) {
            logStr.append("信息【出现异常】");
            logger.error(logStr.toString(), e);
            bol = false;
        }
        return bol;
    }

    public static <V> V get(String key,Class<V> clazz,boolean isTimeOutKey){
        StringBuffer logStr = new StringBuffer();
        logStr.append("向缓存中【获取】对象：").append(key);
        Object result;
        try {
            String resultString = redisServiceStatic.get(key, isTimeOutKey);
            result = JSONObject.parseObject(resultString, clazz);
            return (V) result;
        }catch (Exception e){
            logStr.append("信息【出现异常】");
            logger.error(logStr.toString(), e);
        }
        return null;
    }

    public static <V> List<V> getList(String key,Class<V> clazz,boolean isTimeOutKey){
        StringBuffer logStr = new StringBuffer();
        logStr.append("向缓存中【获取】列表：").append(key);
        List result;
        try {
            String resultString = redisServiceStatic.get(key, isTimeOutKey);
            result = JSONArray.parseArray(resultString, clazz);
            return result;
        }catch (Exception e){
            logStr.append("信息【出现异常】");
            logger.error(logStr.toString(), e);
        }
        return null;
    }



}
