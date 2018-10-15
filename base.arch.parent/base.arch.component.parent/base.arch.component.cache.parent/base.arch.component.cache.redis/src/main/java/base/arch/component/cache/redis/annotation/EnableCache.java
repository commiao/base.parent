package base.arch.component.cache.redis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target({ElementType.METHOD})  
@Retention(RetentionPolicy.RUNTIME)  
public @interface EnableCache  {

    /**
     * 是否开启缓存
     * @return
     */
    boolean isOpen() default true;
    
    /**
     * 缓存的key
     * @return
     */
    String key() ;
    
    /**
     * 获取查询后封装的值
     * @return
     */
    Class<?> value() default Object.class;
    
    /**
     * 发生异常后的 策略
     */
    ExceptionStrategy exceptionStrategy() default ExceptionStrategy.TRY_CATCH;
    
    
    /**
     * 重试次数，默认情况下不进行重试操作(如果事务失败或者redis操作出现其他异常)
     * 当ExceptionStrategy 异常策略设置为ExceptionStrategy.TRY_CATCH的时候才重试操作才有效
     * 否则直接抛出异常，不再进行重试操作
     * @return
     */
    int retryTimes() default 1;
    
    /**
     * 超时时间 与是否配置超时对应，默认单位是秒
     * @see
     * @return
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;
    
    /***
     * 超时时间，默认是0L，表示不超时 
     * @return
     */
    long timeOut() default 0L;
  
    
    /**
     * 缓存类型
     * @return
     */
    CacheType cacheType() default CacheType.REDIS; 
    
    /**
     * 数据类型
     * @return
     */
    DataType dataType() default DataType.STRING;
    
    
    /**
     * 操作策略
     * @return
     */
    KeyStrategy operationStrategy() default KeyStrategy.QUERY;
    
    /**
     * @Description: @EnableCache 注解扩展，预留之后TODO
     * @author jannal
     * @return
     */
    ExtCache[] extCache() default {};
    
    /**
     * key的操作策略
     */
     enum KeyStrategy {
        QUERY,
        SAVE_OR_UPDATE,//保存或更新
        DELETE//删除
        
    }
     /**
      * 异常策略
      */
     enum ExceptionStrategy{
         TRY_CATCH,//捕获redis异常，不影响外围事务
         THROW//直接抛出异常，可能会导致数据库事务回滚
     }
    
}
