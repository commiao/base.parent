package base.arch.component.cache.redis.templ;

import base.arch.component.cache.redis.exception.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
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
     * 时间戳格式.
     */
    private static final String TIME_SUFFIX_KEY_FORMAT = "yyyyMMddHHmmss";

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

    /**
     * @Description::将key添加后缀时间戳. <br/>
     * @author jingmiao
     * @return
     */
    public String getTimeKeyCache(String key){
        if(StringUtils.isEmpty(key)){
            throw new IllegalArgumentException("指定的key前缀不能为空");
        }

        SimpleDateFormat sdf = new SimpleDateFormat(TIME_SUFFIX_KEY_FORMAT);
        String dateStr = sdf.format(new Date());

        return key+"-"+dateStr;
    }

    /**
     * @Description::根据后缀查找最新的key，并将旧key设置失效时间. <br/>
     * @author jingmiao
     * @param prex 去除时间戳"-yyyyMMddHHmmss"之后的key
     * @param isDel 是否将旧的key设置缓存失效时间 默认1分钟后失效
     * @return
     */
    public String getNewKeyCache(String prex, boolean isDel ){
        if(StringUtils.isEmpty(prex)){
            throw new IllegalArgumentException("指定的key前缀不能为空");
        }
        K pattern = (K) ("TO:"+prex+"*");
        Set<K> keys = redisTemplate.keys(pattern);
        Long last_temp = null; // 上次的主键时间戳
        String curr_temp = null;// 该次的主键时间戳
        K new_key = null;// 最新创建的主键
        for(K key : keys){
            curr_temp = getKeySuffixCache(key.toString());
            if(StringUtils.isEmpty(curr_temp)) continue;
            if(last_temp==null || last_temp<Long.valueOf(curr_temp)){// 第一次有效进入 或该次比上次时间新 给上次主键时间戳 赋值
                last_temp = Long.valueOf(curr_temp);
                if(isDel && new_key!=null){// 将旧的key设置缓存失效时间 默认1分钟
                    redisTemplate.expire(new_key, 1, TimeUnit.MINUTES);
                }
                new_key = key;// 将该次主键赋值为  最新创建的时间戳
            }else if(isDel){// 将旧的key设置缓存失效时间 默认1分钟
                redisTemplate.expire(key, 1, TimeUnit.MINUTES);
            }
        }
        return new_key==null?null:new_key.toString();
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

    public String getTimeOutKey(String key, boolean isTimeOutKey) {
        if(isTimeOutKey) {
            key = "TO:"+key;
        }
        return key;
    }

    public static void main(String[] args) {
        System.out.println(getKeyPrexCache("TO:pof:fundListMain:10100:CHG_RAT_1D-DESC-20180410150820"));
        System.out.println(getKeySuffixCache("TO:pof:fundListMain:10100:CHG_RAT_1D-DESC-20180410150820"));
    }

}
