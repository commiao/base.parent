package base.arch.component.cache.redis.util;

import base.arch.component.cache.redis.constant.CacheConstant;
import base.arch.component.cache.redis.service.RedisForStringService;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * CreateDate:2016年10月28日下午5:53:38
 * 
 * @Description: 缓存接口工具类
 * @author:hehch
 * @version V1.0
 */
@Component
public class PofInterfaceCacheUtils {

	@Autowired
	private RedisForStringService redisCacheService;

	private static RedisForStringService redisCacheServiceStatic;

	private static Logger logger = LoggerFactory.getLogger(PofInterfaceCacheUtils.class);

	@PostConstruct
	public void init() {
		redisCacheServiceStatic = redisCacheService;
	}

	/**
	 * @Description::获取数据并保存到缓存中. <br/>
	 * @author hehch
	 * @param joinPoint
	 * @param args
	 * @return
	 * @throws Throwable
	 */
	public static Object getData(ProceedingJoinPoint joinPoint, Object[] args) throws Throwable {
		try {
			return joinPoint.proceed(args);
		} catch (Throwable e) {
		    logger.error(e.getMessage(),e);
			throw e;
		}
	}

	/**
	 * @Description::向缓存中保存数据. <br/>
	 * @author hehch
	 * @param key
	 * @param obj
	 * @param timeOut
	 * @param timeUnit
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean saveToCache(String key, Object obj, long timeOut, TimeUnit timeUnit) {
		StringBuffer logStr = new StringBuffer();
		logStr.append("向缓存中【存储】").append(key);
		try {
			if (obj instanceof PageList) {
				PageList list = (PageList) obj;
				if (list.getPaginator() != null) {
					redisCacheServiceStatic.saveOrUpdateTimeOut(getPaginatorPageKey(key), list.getPaginator().getPage(),
							timeOut, timeUnit);
					redisCacheServiceStatic.saveOrUpdateTimeOut(getPaginatorLimitKey(key),
							list.getPaginator().getLimit(), timeOut, timeUnit);
					redisCacheServiceStatic.saveOrUpdateTimeOut(getPaginatorSizeKey(key),
							list.getPaginator().getTotalCount(), timeOut, timeUnit);
				}
			}
			redisCacheServiceStatic.saveOrUpdateTimeOut(key, obj, timeOut, timeUnit);
			logStr.append("信息，过期时间：").append(timeOut).append(timeUnit.name());
			logger.info(logStr.toString());
			return true;
		} catch (Exception e) {
			logStr.append("信息【出现异常】");
			Object[] cacheResult = PofInterfaceCacheUtils.getObjectJsonFromCache(key);
			boolean isUseCache = (boolean) cacheResult[0];
			if(isUseCache){
				redisCacheServiceStatic.deleteByPrex(key);
			}
			logger.error(logStr.toString(), e.getMessage());
			return false;
		}
	}

	/**
	 * @Description::从缓存中删除数据. <br/>
	 * @author hehch
	 * @param keys
	 * @return
	 */
	public static boolean delFromCache(String... keys) {
		try {
			redisCacheServiceStatic.deleteByKeys(keys);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean deleteByPrex(String var1){
		try {
			redisCacheServiceStatic.deleteByPrex(var1);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	public static boolean deleteBySuffix(String var1){
		try {
			redisCacheServiceStatic.deleteBySuffix(var1);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean setListForTwoHours(String key, List<?> list){
		return setList(key, list, CacheConstant.PofInterfaceCacheConfigConstant.POF_INTERFACE_CACHE_LIVE_TIME_TWO_HOURS, TimeUnit.HOURS);
	}
	
	public static boolean setListForOneDay(String key, List<?> list){
		return setList(key, list, CacheConstant.PofInterfaceCacheConfigConstant.POF_INTERFACE_CACHE_LIVE_TIME_ONE_DAY, TimeUnit.HOURS);
	}
	
	private static boolean setList(String key, List<?> list, long timeOut, TimeUnit timeUnit){
		StringBuffer logStr = new StringBuffer();
		logStr.append("向缓存中【存储】").append(key);
		boolean bol = false;
		try {
			redisCacheServiceStatic.saveOrUpdateTimeOut(key, list, timeOut, timeUnit);
			bol = true;
		} catch (Exception e) {
			logStr.append("信息【出现异常】");
			redisCacheServiceStatic.deleteByPrex(key);
			logger.error(logStr.toString(), e);
			bol = false;
		}
		return bol;
	}
	
	public static <V> List<V> getList(String key,Class<V> clazz){
		List<V> list = null;
		StringBuffer logStr = new StringBuffer();
		logStr.append("从缓存中");
		try {
			list = redisCacheServiceStatic.getList(key, clazz, true);
			if (list == null || list.size() < 1) {
				logStr.append("【未获取到】").append(key).append("信息");
				logger.info(logStr.toString());
			}
		} catch (Exception e) {
			logStr.append("【获取】").append(key).append("信息【出现异常】");
			logger.error(logStr.toString(), e);
		}
		return list;
	}
	
	/**
	 * @Description::从缓存中获取clazz类型数据，返回一个数组，第一个元素是查询缓存是否成功，第二个元素是获取到的缓存数据. <br/>
	 * @author hehch
	 * @param key
	 * @return
	 */
	public static Object[] getObjectJsonFromCache(String key) {
		boolean useDataBase = true;
		String object = null;

		StringBuffer logStr = new StringBuffer();
		logStr.append("从缓存中");
		try {
			object = redisCacheServiceStatic.get(key, String.class, true);
			if (object == null) {
				useDataBase = false;
				logStr.append("【未获取到】").append(key).append("信息");
				logger.info(logStr.toString());
			}
		} catch (Exception e) {
			useDataBase = false;
			logStr.append("【获取】").append(key).append("信息【出现异常】");
			logger.error(logStr.toString(), e);
		}
		return new Object[] { useDataBase, object };
	}

	/**
	 * @Description::从缓存中获取List类型数据，返回一个数组，第一个元素是查询缓存是否成功，第二个元素是获取到的缓存数据. <br/>
	 * @author hehch
	 * @param key
	 * @param clazz
	 * @return
	 */
	public static <V> Object[] getListFromCache(String key, Class<V> clazz) {
		boolean useDataBase = true;
		List<V> list = null;

		StringBuffer logStr = new StringBuffer();
		logStr.append("从缓存中");
		try {
			list = redisCacheServiceStatic.getList(key, clazz, true);
			if (list == null || list.size() < 1) {
				useDataBase = false;
				logStr.append("【未获取到】").append(key).append("信息");
				logger.info(logStr.toString());
			}
		} catch (Exception e) {
			useDataBase = false;
			logStr.append("【获取】").append(key).append("信息【出现异常】");
			logger.error(logStr.toString(), e);
		}
		return new Object[] { useDataBase, list };
	}

	/**
	 * @Description::从缓存中获取PageList类型数据，返回一个数组，第一个元素是查询缓存是否成功， 第二个元素是获取到的缓存数据.
	 *                                                         <br/>
	 * @author hehch
	 * @param key
	 * @param clazz
	 * @return
	 */
	public static <V> Object[] getPageListFromCache(String key, Class<V> clazz) {
		boolean useDataBase = true;
		PageList<V> pageList = null;

		StringBuffer logStr = new StringBuffer();
		logStr.append("从缓存中");
		try {
			List<V> list = redisCacheServiceStatic.getList(key, clazz, true);
			if (list == null || list.size() < 1) {
				useDataBase = false;
				logStr.append("【未获取到】").append(key).append("信息");
				logger.info(logStr.toString());
			}
			Integer page = redisCacheServiceStatic.get(getPaginatorPageKey(key), Integer.class, true);
			Integer limit = redisCacheServiceStatic.get(getPaginatorLimitKey(key), Integer.class, true);
			Integer size = redisCacheServiceStatic.get(getPaginatorSizeKey(key), Integer.class, true);
			Paginator paginator = null;
			if (page != null && limit != null && size != null) {
				paginator = new Paginator(page, limit, size);
			}
			if(null!=list){
			    pageList = new PageList<V>(list, paginator);
            }
		} catch (Exception e) {
			useDataBase = false;
			logStr.append("【获取】").append(key).append("信息【出现异常】");
			logger.error(logStr.toString(), e);
		} finally {
			if (pageList == null) {
				pageList = new PageList<V>();
			}
		}
		return new Object[] { useDataBase, pageList };
	}

	/**
	 * @Description::获取分页元素pagekey. <br/>
	 * @author hehch
	 * @param key
	 * @return
	 */
	private static String getPaginatorPageKey(String key) {
		return key
				+ CacheConstant.PofInterfaceCacheConfigConstant.POF_INTERFACE_CACHE_PAGINATOR_PAGE_SUFFIX;
	}

	/**
	 * @Description::获取分页元素limitkey. <br/>
	 * @author hehch
	 * @param key
	 * @return
	 */
	private static String getPaginatorLimitKey(String key) {
		return key
				+ CacheConstant.PofInterfaceCacheConfigConstant.POF_INTERFACE_CACHE_PAGINATOR_LIMIT_SUFFIX;
	}

	/**
	 * @Description::获取分页元素sizekey. <br/>
	 * @author hehch
	 * @param key
	 * @return
	 */
	private static String getPaginatorSizeKey(String key) {
		return key
				+ CacheConstant.PofInterfaceCacheConfigConstant.POF_INTERFACE_CACHE_PAGINATOR_SIZE_SUFFIX;
	}
	
	
	/** 
	 * @Description::(适用于比较最新数据与缓存进行比较，并更新缓存操作 true:数据不一致进行了更新  false:数据一致未进行更新   null:数据为空). <br/> 
	 * @author jingmiao
	 * @param key
	 * @param newList
	 * @param clazz
	 * @return
	 */ 
	public static <V>Boolean saveOrUpdateListCache(String key,List<V> newList,Class<V> clazz){
		Boolean isDo = null;
		List<V> cacheList = getList(key, clazz);
		Boolean isSame = compareList(cacheList, newList);
		if(isSame!=null){
			if(!isSame){// 需要更新缓存
				setListForTwoHours(key, newList);
				isDo = true;
			}else{
				isDo = false;
			}
		}else{
			delFromCache(key);
		}
		return isDo;
	}
	
	private static Boolean compareList(List<?> oldList, List<?> newList){
		if(newList==null) return null;
		if(oldList==null) return false;
		if(newList.size()!=oldList.size()) return false;
		for(int i=0;i<oldList.size();i++){
			if(!oldList.get(i).equals(newList.get(i))){
				return false;
			}
		}
		return true;
	}

}
