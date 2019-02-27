package base.arch.component.cache.redis.handle;

import base.arch.component.cache.redis.service.RedisForListService;
import base.arch.component.cache.redis.util.JsonUtils;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @CreateDate: 2018/7/30 14:08
 * @Description: 时间缓存控制
 * @author: jingmiao
 * @version: V1.0
 */
@Component
public class HandleListCacheTimer {

    @Autowired
    private RedisForListService redisForListService;

    private static RedisForListService redisForListServiceStatic;

    private static Logger logger = LoggerFactory.getLogger(HandleListCacheTimer.class);

    @PostConstruct
    public void init() {
        redisForListServiceStatic = redisForListService;
    }


    public static String getNewKeyCache(String key, boolean isDel){
        return redisForListServiceStatic.getNewKeyCache(key, isDel);
    }

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
            String new_key = redisForListServiceStatic.getNewKeyCache(key, true);
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
     * @Description::向缓存中添加list<T>并删除旧的主键. <br/>
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
       return setListCacheBase(key,JsonUtils.list2Json(listBean),time,timeUnit,isDel);
    }

    /**
     * 向缓存中添加list并删除旧的主键
     * @param key
     * @param list
     * @param time
     * @param timeUnit
     * @param isDel
     * @return
     */
    private static boolean setListCacheBase(String key, List<String> list, long time, TimeUnit timeUnit, boolean isDel) {
        if(list==null || list.size()<1) {
            logger.info("setListCacheBase更新缓存，期望更新list不为空，实际传入空，返回false");
            return false;
        }
        try {
            String time_key = redisForListServiceStatic.getTimeKeyCache(key);
            boolean isSuc = redisForListServiceStatic.setList("TO:"+time_key, list, time, timeUnit);
            if(isDel){
                redisForListServiceStatic.getNewKeyCache(key, true);
            }
            return isSuc;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public static <T>List<T> getListCache(String key, Class<T> clazz ){
        List<T> list = null;
        List<String> jsonList = getListCacheBase(key);
        if(jsonList!=null && jsonList.size()>0) {
            list = JsonUtils.json2List(jsonList, clazz);
        }
        return list;
    }

    private static List<String> getListCacheBase(String key){
        String new_key = redisForListServiceStatic.getNewKeyCache(key, true);
        List<String> jsonList = null;
        if(new_key!=null) {
            jsonList = redisForListServiceStatic.getList(new_key, 0, -1);
        }
        return jsonList;
    }

    public static long getListSizeCache(String key) {
        String new_key = redisForListServiceStatic.getNewKeyCache(key, true);
        return redisForListServiceStatic.getListSize(new_key);
    }

}
