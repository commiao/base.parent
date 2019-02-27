package base.arch.component.cache.redis.annotation;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @CreateDate: 2018/11/28 17:01
 * @Description: TODO
 * @author: jingmiao
 * @version: V1.0
 */
public class CacheAnnotationParam {
    String key;
    boolean useCache = true;
    String dateFormat; // "yyyy-MM-dd HH:mm:ss.SSS"

/** 从注解参数中获取 start **/
    String prexKey;// 缓存根路径
    Class cacheType;// 缓存存储类型
    long timeOut;// 缓存超时时间
    TimeUnit timeUnit;// 缓存超时时间单位
    String description;// 描述信息
    int jsonPareType;// json解析方式
/** 从注解参数中获取 end **/

/** 从方法中获取 start **/
    Class returnClazz;// 返回参数类型
    List<Class<?>> returnBeanClazz;// 返回参数的泛型list
    String methodName;// 方法名称
/** 从方法中获取 end **/

    public CacheAnnotationParam(String key) {
        this.key = key;
    }

    public CacheAnnotationParam(String key, boolean useCache) {
        this.key = key;
        this.useCache = useCache;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isUseCache() {
        return useCache;
    }

    public void setUseCache(boolean useCache) {
        this.useCache = useCache;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getPrexKey() {
        return prexKey;
    }

    public void setPrexKey(String prexKey) {
        this.prexKey = prexKey;
    }

    public Class getCacheType() {
        return cacheType;
    }

    public void setCacheType(Class cacheType) {
        this.cacheType = cacheType;
    }

    public long getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(long timeOut) {
        this.timeOut = timeOut;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Class getReturnClazz() {
        return returnClazz;
    }

    public void setReturnClazz(Class returnClazz) {
        this.returnClazz = returnClazz;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public int getJsonPareType() {
        return jsonPareType;
    }

    public void setJsonPareType(int jsonPareType) {
        this.jsonPareType = jsonPareType;
    }

    public List<Class<?>> getReturnBeanClazz() {
        return returnBeanClazz;
    }

    public void setReturnBeanClazz(List<Class<?>> returnBeanClazz) {
        this.returnBeanClazz = returnBeanClazz;
    }
}
