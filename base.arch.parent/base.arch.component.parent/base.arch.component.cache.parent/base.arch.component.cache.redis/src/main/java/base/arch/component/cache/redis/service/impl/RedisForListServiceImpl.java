package base.arch.component.cache.redis.service.impl;

import base.arch.component.cache.redis.exception.CacheException;
import base.arch.component.cache.redis.service.RedisForListService;
import base.arch.component.cache.redis.templ.RedisTempl;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisForListServiceImpl extends RedisTempl<String,String> implements RedisForListService {

    /**
     * 获取list缓存的内容 
     * @param key 键 
     * @param start 开始 
     * @param end 结束  0 到 -1代表所有值 
     * @return 
     */  
    @Override
    public List<String> getList(String key,long start, long end){
        List<String> list = null;
        try {  
        	ListOperations<String, String> vo = redisTemplate.opsForList();
            list = vo.range(key, start, end);
        } catch (Exception e) {
            throw new CacheException(key + "存入值发生错误,具体原因如下：" + e.getMessage(), e);
        } finally {

            return list;
        }
    }
    
    /** 
     * 获取list缓存的长度 
     * @param key 键 
     * @return 
     */  
    @Override
    public long getListSize(String key){  
        try {  
            return redisTemplate.opsForList().size(key);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return 0;  
        }  
    }  
      
    /** 
     * 通过索引 获取list中的值 
     * @param key 键 
     * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推 
     * @return 
     */  
    @Override
    public String getByIndex(String key,long index){  
        try {  
            return redisTemplate.opsForList().index(key, index); 
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  
      
    /** 
     * 向list<String>中插入数据
     * @param key 键 
     * @param json 值 
     * @param time 时间(秒) 
     * @return 
     */  
    @Override
    public boolean addBean(String key, String json, long time, TimeUnit timeUnit) {
        try {  
            redisTemplate.opsForList().rightPush(key, json);  
            if (time > 0) expire(key, time, timeUnit);  
            return true;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
    }  
      
    /** 
     * 将list放入缓存 
     * @param key 键 
     * @param jsonList 值 
     * @param time 时间(秒) 
     * @return 
     */  
    @Override
    public boolean setList(String key, List<String> jsonList, long time, TimeUnit timeUnit) {
        try {  
        	ListOperations<String, String> lo = redisTemplate.opsForList();
            lo.rightPushAll(key, jsonList); 
            if (time > 0) expire(key, time, timeUnit); 
            return true;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
    }  
      
    /** 
     * 根据索引修改list中的某条数据 
     * @param key 键 
     * @param index 索引 
     * @param json 值 
     * @return 
     */  
    @Override
    public boolean updateByIndex(String key, long index,String json) {  
        try {  
            redisTemplate.opsForList().set(key, index, json);  
            return true;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
    }   
      
    /** 
     * 移除N个值为value  
     * @param key 键 
     * @param count 移除多少个 
     * @param json 值 
     * @return 移除的个数 
     */ 
    @Override
    public long removeBean(String key,long count, String json) {  
        try {  
            Long remove = redisTemplate.opsForList().remove(key, count, json);  
            return remove;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return 0;  
        }  
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
