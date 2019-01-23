package base.arch.component.cache.redis.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @CreateDate: 2018/11/27 15:14
 * @Description: TODO
 * @author: jingmiao
 * @version: V1.0
 */
@Target({ ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CacheAnnotation {
    /**
     * @return 缓存根路径
     */
    String prexKey();
    /**
     * @return 缓存存储类型
     */
    Class cacheType() default String.class;
    /**
     * @return 缓存超时时间
     */
    long timeOut() default 2l;
    /**
     * @return 缓存超时时间单位
     */
    TimeUnit timeUnit() default TimeUnit.HOURS;
    /**
     * @return 描述信息
     */
    String description() default "没有描述信息";
    /**
     * @return json解析方式 0：fastjson； 1：org
     */
    int jsonPareType() default 0;
}
