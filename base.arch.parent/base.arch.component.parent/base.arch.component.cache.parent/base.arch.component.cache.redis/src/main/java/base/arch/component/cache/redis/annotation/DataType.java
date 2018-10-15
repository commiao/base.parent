package base.arch.component.cache.redis.annotation;

/**
 * CreateDate:2016年9月14日下午2:56:26 
 * @Description: 缓存对应的存储的数据类型 
 * @see basic.arch.component.cache.redis
 * @author:jannal
 * @version V1.0
 */
public enum DataType{
    STRING,
    HASHSET,
    LIST,
    SET,
    SORTEDSET;
    public static  String getDescription(){
        return "String,hashSet,List,Set,SORTEDEST";
    }
    
}