package base.arch.component.cache.redis.handle;

import base.arch.component.cache.redis.constant.CacheConstant;
import base.arch.component.cache.redis.service.RedisService;
import base.arch.component.cache.redis.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;
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


    public static boolean setObjectForTwoHours(String key, Object obj){
        return setObjectCache(key, obj, CacheConstant.PofInterfaceCacheConfigConstant.POF_INTERFACE_CACHE_LIVE_TIME_TWO_HOURS, TimeUnit.MINUTES);
    }
    public static boolean setObjectForOneDay(String key, Object obj){
        return setObjectCache(key, obj, CacheConstant.PofInterfaceCacheConfigConstant.POF_INTERFACE_CACHE_LIVE_TIME_ONE_DAY, TimeUnit.HOURS);
    }

    private static boolean setObjectCache(String key, Object obj, long timeOut, TimeUnit timeUnit){
        StringBuffer logStr = new StringBuffer();
        String time_key = getTimeKeyCache(key);
        logStr.append("向缓存中【存储】时间戳对象：").append(time_key);
        boolean bol = false;
        try {
            String json = JsonUtils.bean2Json(obj);
            bol = redisServiceStatic.put(time_key, json, timeOut, timeUnit);
        } catch (Exception e) {
            logStr.append("信息【出现异常】");
//			redisCacheServiceStatic.deleteByPrex(key);
            logger.error(logStr.toString(), e);
            bol = false;
        }
        if(bol){
            getNewKeyCache(key,true);
        }
        return bol;
    }

    public static <V> V getObjectCache(String key,Class<V> clazz){
        String new_key = getNewKeyCache(key, true);
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
            v = JsonUtils.json2ObjStrong(json, clazz);
        } catch (Exception e) {
//			redisCacheServiceStatic.deleteByPrex(key);
            logger.debug("从缓存中查询key：{}-解析失败",new_key);
        }
        return v;
    }

    public static <V> V getObjectCache(String key,Class<V> clazz,Map<String, Class<?>> classMap){
        String new_key = getNewKeyCache(key, true);
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

	/* ================================================ redis 主键工具 ====================================================================== */

    /**
     * 时间戳格式.
     */
    private static final String TIME_SUFFIX_KEY_FORMAT = "yyyyMMddHHmmss";


    /**
     * @Description::根据后缀查找最新的key，并将旧key设置失效时间. <br/>
     * @author jingmiao
     * @param prex 去除时间戳"-yyyyMMddHHmmss"之后的key
     * @param isDel 是否将旧的key设置缓存失效时间 默认1分钟后失效
     * @return
     */
    public static String getNewKeyCache(String prex, boolean isDel ){
        if(StringUtils.isEmpty(prex)){
            throw new IllegalArgumentException("指定的key前缀不能为空");
        }
        Set<String> keys = redisServiceStatic.keys("TO:"+prex+"*");
        Long last_temp = null; // 上次的主键时间戳
        String curr_temp = null;// 该次的主键时间戳
        String new_key = null;// 最新创建的主键
        for(String key : keys){
            curr_temp = getKeySuffixCache(key);
            if(StringUtils.isEmpty(curr_temp)) continue;
            if(last_temp==null || last_temp<Long.valueOf(curr_temp)){// 第一次有效进入 或该次比上次时间新 给上次主键时间戳 赋值
                last_temp = Long.valueOf(curr_temp);
                if(isDel && new_key!=null){// 将旧的key设置缓存失效时间 默认1分钟
                    redisServiceStatic.expire(new_key, 1, TimeUnit.MINUTES);
                }
                new_key = key;// 将该次主键赋值为  最新创建的时间戳
            }else if(isDel){// 将旧的key设置缓存失效时间 默认1分钟
                redisServiceStatic.expire(key, 1, TimeUnit.MINUTES);
            }
        }
        return new_key;
    }


    /**
     * @Description::将key添加后缀时间戳. <br/>
     * @author jingmiao
     * @return
     */
    public static String getTimeKeyCache(String key){
        if(StringUtils.isEmpty(key)){
            throw new IllegalArgumentException("指定的key前缀不能为空");
        }

        SimpleDateFormat sdf = new SimpleDateFormat(TIME_SUFFIX_KEY_FORMAT);
        String dateStr = sdf.format(new Date());

        return key+"-"+dateStr;
    }

    /**
     * @Description::获取key的时间戳后缀. <br/>
     * @author jingmiao
     * @param key
     * @return
     */
    private static String getKeySuffixCache(String key){
        if(!isTimeKeyCache(key)){
            return null;
        }
        return key.substring(key.length()-TIME_SUFFIX_KEY_FORMAT.length(), key.length());
    }

    /**
     * @Description::去除key的时间戳后缀. <br/>
     * @author jingmiao
     * @param key
     * @return
     */
    private static String getKeyPrexCache(String key){
        if(!isTimeKeyCache(key)){
            return key;
        }
        return key.substring(0, key.length()-TIME_SUFFIX_KEY_FORMAT.length()-1);
    }

    private static boolean isTimeKeyCache(String key){
        if(StringUtils.isEmpty(key)){
            return false;
        }
        if(key.length()<TIME_SUFFIX_KEY_FORMAT.length()+1){
            return false;
        }
        String suffix = key.substring(key.length()-TIME_SUFFIX_KEY_FORMAT.length(), key.length());
        try {
            Long.valueOf(suffix);
        } catch (Exception e) {
            return false;
        }
        String flag = key.substring(key.length()-TIME_SUFFIX_KEY_FORMAT.length()-1, key.length()-TIME_SUFFIX_KEY_FORMAT.length());
        if(!flag.trim().equals("-")){
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(getKeyPrexCache("TO:pof:fundListMain:10100:CHG_RAT_1D-DESC-20180410150820"));
        System.out.println(getKeySuffixCache("TO:pof:fundListMain:10100:CHG_RAT_1D-DESC-20180410150820"));
    }

}
