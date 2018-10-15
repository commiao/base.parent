package base.arch.component.cache.redis.service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface RedisForListService {

	/**
     * 指定缓存失效时间
     * @author jingmiao
     * @param key 键
     * @param time 时间(秒)
     * @param timeUnit 时间单位
     * @return
     */
	boolean expire(String key, long time, TimeUnit timeUnit);

	/**
	 * @Description::查询list<String>列表. <br/> 
	 * @author jingmiao
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */ 
	List<String> getList(String key, long start, long end);

	/** 
	 * @Description::获取列表list<String>的总记录条数. <br/> 
	 * @author jingmiao
	 * @param key
	 * @return
	 */ 
	long getListSize(String key);

	/** 
	 * @Description::根据list<String>下标获取对应的数据. <br/> 
	 * @author jingmiao
	 * @param key
	 * @param index
	 * @return
	 */ 
	String getByIndex(String key, long index);

	/** 
	 * @Description::向list<String>中插入数据. <br/> 
	 * @author jingmiao
	 * @param key
	 * @param json
	 * @param time
	 * @param timeUnit
	 * @return
	 */ 
	boolean addBean(String key, String json, long time, TimeUnit timeUnit);

	/** 
	 * @Description::保存一个list<String>缓存. <br/> 
	 * @author jingmiao
	 * @param key
	 * @param jsonList
	 * @param time
	 * @param timeUnit
	 * @return
	 */ 
	boolean setList(String key, List<String> jsonList, long time, TimeUnit timeUnit);

	/** 
	 * @Description::根据list<String>下标更新该数据. <br/> 
	 * @author jingmiao
	 * @param key
	 * @param index
	 * @param json
	 * @return
	 */ 
	boolean updateByIndex(String key, long index, String json);

	/** 
	 * @Description::移除key中值为json的count个,返回删除的个数；如果没有这个元素则返回0 . <br/> 
	 * @author jingmiao
	 * @param key
	 * @param count
	 * @param json
	 * @return
	 */ 
	long removeBean(String key, long count, String json);

	/** 
     * @Description::查询主键set集合. <br/> 
     * *：通配任意多个字符.<br/> 
     * ?：通配单个字符.<br/> 
     * []：通配括号内的某一个字符.<br/> 
     * @author jingmiao
     * @param pattern
     * @return
     */ 
	Set<String> keys(String pattern);


}
