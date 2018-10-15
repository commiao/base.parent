package base.arch.component.cache.redis.handle;

import base.arch.component.cache.redis.service.RedisForListService;
import base.arch.component.cache.redis.service.RedisHashService;
import base.arch.component.cache.redis.util.JsonUtils;
import com.alibaba.fastjson.JSON;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @CreateDate: 2018/7/30 14:08
 * @Description: 时间缓存控制
 * @author: jingmiao
 * @version: V1.0
 */
@Component
public class HandleCacheTimer {

    @Autowired
    private RedisForListService redisForListService;

    @Autowired
    private RedisHashService redisHashService;

    private static RedisForListService redisForListServiceStatic;

    private static RedisHashService redisHashServiceStatic;

    private static Logger logger = LoggerFactory.getLogger(HandleCacheTimer.class);

    @PostConstruct
    public void init() {
        redisForListServiceStatic = redisForListService;
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
                List<String> listJson = JsonUtils.json2Arr(json, String.class);
                result = JsonUtils.json2List(listJson, clazz);
            }
        } catch (Exception e) {
            logger.debug("从缓存中查询key：{}-hsahKey：{}-查询失败",key,hashKey);
        }
        return result;
    }

    public static <V>boolean setHashMap(String key, Map<String,V> map, long timeOut, TimeUnit timeUnit){
        Map<String,String> result = new HashMap<>();
        for(Map.Entry<String,V> entity:map.entrySet()){
            result.put(entity.getKey(),JsonUtils.bean2Json(entity.getValue()));
        }
        String time_key = getTimeKeyCache(key);
        boolean isSuc = redisHashServiceStatic.put(time_key,result,timeOut,timeUnit);
        if(isSuc){
            getNewKeyCache(key, true);
        }
        return isSuc;
    }

    public static <V> Map<String,V> getHashMap(String key, Class<V> clazz){
        Map<String,V> result = new HashMap<>();
        String time_key = getNewKeyCache(key, true);
        // time_key 已处理TO前缀问题
        Map<Object,Object> map = redisHashServiceStatic.get(time_key,false);
        for(Map.Entry<Object,Object> entity:map.entrySet()){
//            JsonUtils.json2Obj(entity.getValue().toString(),clazz)
            result.put(entity.getKey().toString(),JsonUtils.json2ObjStrong(entity.getValue().toString(), clazz));
        }
        return result;
    }

    public static boolean setHashCache(String key, Object hashKey, Object value, long timeOut, TimeUnit timeUnit){
        return redisHashServiceStatic.put(key, hashKey, value, timeOut,timeUnit);
    }

    public static boolean setHashTimeOutCache(String key, long timeOut, TimeUnit timeUnit){
        return redisHashServiceStatic.expire(key, timeOut, timeUnit);
    }

    public static boolean setHashCache(String key, String hashKey, Object value, boolean isTimeOutKey){
        return redisHashServiceStatic.put(key, hashKey, value, isTimeOutKey);
    }
    public static <T>boolean setHashList(String key, String hashKey, List<T> list, boolean isTimeOutKey){
        return redisHashServiceStatic.put(key, hashKey, JSON.toJSONString(JSON.toJSON(list)), isTimeOutKey);
    }

	/* ================================================ redis list ====================================================================== */

    /**
     * 获取pageList缓存的内容
     * @param key 键
     * @param startPage 起始页 默认1
     * @param pageSize 每页显示条数 默认10
     * @return
     */
    public static <T>PageList<T> getPageListCache(String key, Integer startPage, Integer pageSize, Class<T> clazz, boolean isDel){
        PageList<T> pageList = null;
        if(startPage==null || startPage<1) startPage = 1;
        if(pageSize==null || pageSize<1) pageSize = 10;

        long start,end;
        start = (startPage-1)*pageSize;
        end = start + pageSize - 1;

        try {
            String new_key = getNewKeyCache(key, true);
            if(org.apache.commons.lang3.StringUtils.isBlank(new_key)) {
                logger.debug("获取pageList缓存的内容：未获取到最新日期后缀的主键key：{},返回null",new_key);
                return pageList;
            }
            logger.debug("获取pageList缓存的内容：获取最新日期后缀的主键new_key:{}",new_key);
            List<String> jsonList = redisForListServiceStatic.getList(new_key, start, end);
            if(jsonList==null || jsonList.size()<1){
                logger.error("根据最新时间主键key：{}获取到的缓存内容为null，出现异常！",new_key);
                return pageList;
            }
            List<T> list = JsonUtils.json2List(jsonList, clazz);
            long totalCount = redisForListServiceStatic.getListSize(new_key);

            if(!list.isEmpty() && list.size()>0 && totalCount>0){
                Paginator paginator = new Paginator(startPage, pageSize, new Long(totalCount).intValue());
                pageList = new PageList<>(list, paginator);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return pageList;
    }

    /**
     * @Description::向缓存中添加list并删除旧的主键. <br/>
     * @author jingmiao
     * @param key
     * @param listBean
     * @param time
     * @param timeUnit
     * @param isDel
     * @return
     */
    public static <T>boolean setListCache(String key, List<T> listBean, long time, TimeUnit timeUnit, boolean isDel) {
        if(listBean==null || listBean.size()<1) {
            logger.info("setListCache更新缓存，期望更新listBean不为空，实际传入空，返回false");
            return false;
        }
        try {
            String time_key = getTimeKeyCache(key);
            boolean isSuc = redisForListServiceStatic.setList("TO:"+time_key, JsonUtils.list2Json(listBean), time, timeUnit);
            if(isSuc){
                getNewKeyCache(key, true);
            }
            return isSuc;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static <T>List<T> getListCache(String key, Class<T> clazz ){
        String new_key = getNewKeyCache(key, true);
        List<T> list = null;
        if(new_key!=null) {
            List<String> jsonList = redisForListServiceStatic.getList(new_key, 0, -1);
            list = JsonUtils.json2List(jsonList, clazz);
        }
        return list;
    }

    public static long getListSizeCache(String key) {
        String new_key = getNewKeyCache(key, true);
        return redisForListServiceStatic.getListSize(new_key);
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
        Set<String> keys = redisForListServiceStatic.keys("TO:"+prex+"*");
        Long last_temp = null; // 上次的主键时间戳
        String curr_temp = null;// 该次的主键时间戳
        String new_key = null;// 最新创建的主键
        for(String key : keys){
            curr_temp = getKeySuffixCache(key);
            if(StringUtils.isEmpty(curr_temp)) continue;
            if(last_temp==null || last_temp<Long.valueOf(curr_temp)){// 第一次有效进入 或该次比上次时间新 给上次主键时间戳 赋值
                last_temp = Long.valueOf(curr_temp);
                if(isDel && new_key!=null){// 将旧的key设置缓存失效时间 默认1分钟
                    redisForListServiceStatic.expire(new_key, 1, TimeUnit.MINUTES);
                }
                new_key = key;// 将该次主键赋值为  最新创建的时间戳
            }else if(isDel){// 将旧的key设置缓存失效时间 默认1分钟
                redisForListServiceStatic.expire(key, 1, TimeUnit.MINUTES);
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
