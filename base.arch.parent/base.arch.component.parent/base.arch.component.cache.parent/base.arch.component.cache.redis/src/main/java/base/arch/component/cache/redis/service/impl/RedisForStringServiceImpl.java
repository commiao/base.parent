//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package base.arch.component.cache.redis.service.impl;

import base.arch.component.cache.redis.annotation.HashExcludeColumn;
import base.arch.component.cache.redis.config.IKeyConfig;
import base.arch.component.cache.redis.config.RedisKeyConfigImpl;
import base.arch.component.cache.redis.contextholder.RedisSpringApplicationContextHolder2;
import base.arch.component.cache.redis.exception.CacheException;
import base.arch.component.cache.redis.service.RedisForStringService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;
import org.springframework.util.ReflectionUtils.FieldFilter;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class RedisForStringServiceImpl implements RedisForStringService {
    @Resource(name="redisTemplate")
    protected RedisTemplate<String, ?> redisTemplate;
    protected IKeyConfig redisKeyConfig;
    private Logger logger = LoggerFactory.getLogger(RedisForStringServiceImpl.class);

    public RedisForStringServiceImpl() {
    }

    public RedisForStringServiceImpl(IKeyConfig redisKeyConfig) {
        this.redisKeyConfig = redisKeyConfig;
    }

    public void deleteByKeys(String... keys) throws CacheException {
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

    public void deleteByPrex(String prex) throws CacheException {
        if(StringUtils.isEmpty(prex)) {
            throw new IllegalArgumentException("指定删除的key前缀不能为空");
        } else {
            Set<String> keys = this.redisTemplate.keys(prex + "*");
            this.redisTemplate.delete(keys);
        }
    }

    public void deleteBySuffix(String suffix) throws CacheException {
        if(StringUtils.isEmpty(suffix)) {
            throw new IllegalArgumentException("指定删除的key后缀不能为空");
        } else {
            Set<String> keys = this.redisTemplate.keys("*" + suffix);
            this.redisTemplate.delete(keys);
        }
    }

    public Long incrNoTimeOut(String key, Long value) throws CacheException {
        Long executeResult = null;

        try {
            executeResult = (Long)this.redisTemplate.execute(this.incrSessionCallback(key, value, false, 0L, TimeUnit.SECONDS));
            return executeResult;
        } catch (Exception var5) {
            throw new CacheException(key + "存入值发生错误,具体原因如下：" + var5.getMessage(), var5);
        }
    }

    public Long incrTimeOut(String key, Long value, long liveTime, TimeUnit timeUnit) throws CacheException {
        this.validateTimeOut(liveTime, timeUnit);
        Long executeResult = null;

        try {
            executeResult = (Long)this.redisTemplate.execute(this.incrSessionCallback(this.getRedisKeyConfig().getTimeOutKey(key), value, true, liveTime, timeUnit));
            return executeResult;
        } catch (Exception var8) {
            throw new CacheException(key + "存入值发生错误,具体原因如下：" + var8.getMessage(), var8);
        }
    }

    public <V> void saveOrUpdateTimeOut(String key, V value, long liveTime, TimeUnit timeUnit) throws CacheException {
        this.validateTimeOut(liveTime, timeUnit);

        try {
            String valueString = JSONObject.toJSONString(value);
            this.redisTemplate.execute(this.saveOrUpdateSessionCallback(this.getRedisKeyConfig().getTimeOutKey(key), valueString, true, liveTime, timeUnit));
        } catch (Exception var7) {
            throw new CacheException(key + "存入值发生错误,具体原因如下：" + var7.getMessage(), var7);
        }
    }

    public <V> void saveOrUpdateNoTimeOut(String key, V value) throws CacheException {
        this.saveOrUpdate(key, value, false, 0L, TimeUnit.SECONDS);
    }

    public <V> V get(String key, Class<V> clazz, boolean isTimeOutKey) throws CacheException {
        Object result = null;

        try {
            Object resultObject = this.redisTemplate.execute(this.getSessionCallback(this.getTimeOutKey(key, isTimeOutKey)));
            if(resultObject != null) {
                String resultString = (String)resultObject;
                result = JSONObject.parseObject(resultString, clazz);
            }

            return (V)result;
        } catch (Exception var7) {
            throw new CacheException(key + "获取值发生错误,具体原因如下：" + var7.getMessage(), var7);
        }
    }

    public <V> List<V> getList(String key, Class<V> clazz, boolean isTimeOutKey) throws CacheException {
        List result = null;

        try {
            Object resultObject = this.redisTemplate.execute(this.getSessionCallback(this.getTimeOutKey(key, isTimeOutKey)));
            if(resultObject != null) {
                String resultString = (String)resultObject;
                result = JSONArray.parseArray(resultString, clazz);
            }

            return result;
        } catch (Exception var7) {
            throw new CacheException(key + "获取值发生错误,具体原因如下：" + var7.getMessage(), var7);
        }
    }

    public <V> Long rpush(final String key, final V value, final long liveTime, final boolean isSetTimeOut, final TimeUnit timeUnit) throws CacheException {
        Long listLength = (Long)this.redisTemplate.execute(new SessionCallback<Long>() {
            public Long execute(RedisOperations operations) throws DataAccessException {
                RedisForStringServiceImpl.this.startTransaction(key, operations);
                BoundListOperations boundListOps = operations.boundListOps(key);
                String valueString = JSONObject.toJSONString(value);
                boundListOps.rightPush(valueString);
                if(isSetTimeOut) {
                    boundListOps.expire(liveTime, timeUnit);
                }

                List execResult = RedisForStringServiceImpl.this.commitTransaction(operations, key);
                Long curlistLength = null;
                if(execResult.size() > 0) {
                    curlistLength = (Long)execResult.get(0);
                }

                return curlistLength;
            }
        });
        return listLength;
    }

    public <V> void hashSetSaveOrUpdateNoTimeOut(String key, V value) throws CacheException {
        this.hashSaveOrUpdate(key, value, 0L, false, TimeUnit.SECONDS);
    }

    public <V> V hashSetGet(String key, final Class<V> clazz, boolean isTimeOutKey) {
        final String timeOutKey = this.getTimeOutKey(key, isTimeOutKey);
        return this.redisTemplate.execute(new SessionCallback<V>() {
            public V execute(RedisOperations operations) throws DataAccessException {
                BoundHashOperations boundHashOps = operations.boundHashOps(timeOutKey);
                Set keys = boundHashOps.keys();
                Map entries = boundHashOps.entries();
                Object newInstance = null;

                try {
                    newInstance = clazz.newInstance();
                    BeanUtils.populate(newInstance, entries);
                    return (V)newInstance;
                } catch (IllegalAccessException var7) {
                    throw new CacheException("key为" + timeOutKey + "从hashset中取出来转换成Bean发生异常", var7);
                } catch (InvocationTargetException var8) {
                    throw new CacheException("key为" + timeOutKey + "从hashset中取出来转换成Bean发生异常", var8);
                } catch (InstantiationException var9) {
                    throw new CacheException("key为" + timeOutKey + "从hashset中取出来转换成Bean发生异常", var9);
                }
            }
        });
    }

    public <V> void hashSetSaveOrUpdateTimeOut(String key, V value, long liveTime, boolean isSetTimeOut, TimeUnit timeUnit) throws CacheException {
        this.validateTimeOut(liveTime, timeUnit);
        key = this.getTimeOutKey(key, true);
        this.hashSaveOrUpdate(key, value, liveTime, isSetTimeOut, timeUnit);
    }

    private <V> void hashSaveOrUpdate(final String key, final V value, final long liveTime, final boolean isSetTimeOut, final TimeUnit timeUnit) {
        this.redisTemplate.execute(new SessionCallback<V>() {
            public Object execute(RedisOperations operations) throws DataAccessException {
                RedisForStringServiceImpl.this.startTransaction(key, operations);
                BoundHashOperations boundHashOps = operations.boundHashOps(key);
                final Map map = new HashMap();
                ReflectionUtils.doWithFields(value.getClass(), new FieldCallback() {
                    public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                        int modifiers = field.getModifiers();
                        if(Modifier.isPrivate(modifiers)) {
                            field.setAccessible(true);
                        }

                        String name = field.getName();
                        Object object = field.get(value);
                        if(object != null) {
                            if(object.getClass() != String.class) {
                                String valueString = JSONObject.toJSONString(object);
                                map.put(name, valueString);
                            } else {
                                map.put(name, object);
                            }
                        }

                    }
                }, new FieldFilter() {
                    public boolean matches(Field field) {
                        return ReflectionUtils.COPYABLE_FIELDS.matches(field) && !field.isAnnotationPresent(HashExcludeColumn.class);
                    }
                });
                boundHashOps.putAll(map);
                if(isSetTimeOut) {
                    boundHashOps.expire(liveTime, timeUnit);
                }

                List execResult = RedisForStringServiceImpl.this.commitTransaction(operations, key);
                return execResult;
            }
        });
    }

    private String getTimeOutKey(String key, boolean isTimeOutKey) {
        if(isTimeOutKey) {
            key = this.getRedisKeyConfig().getTimeOutKey(key);
        }

        return key;
    }

    public void validateTimeOut(long liveTime, TimeUnit timeUnit) {
        if(liveTime <= 0L) {
            throw new IllegalArgumentException("超时时间liveTime必须指定并且大于0");
        } else if(timeUnit == null) {
            throw new IllegalArgumentException("timeUnit必须指定，不能为空");
        }
    }

    private <V> void saveOrUpdate(String key, V value, boolean isSetTimeOut, long liveTime, TimeUnit timeUnit) throws CacheException {
        try {
            String valueString = JSONObject.toJSONString(value);
            this.redisTemplate.execute(this.saveOrUpdateSessionCallback(key, valueString, isSetTimeOut, liveTime, timeUnit));
        } catch (Exception var8) {
            throw new CacheException(key + "存入值发生错误,具体原因如下：" + var8.getMessage(), var8);
        }
    }

    private SessionCallback incrSessionCallback(final String key, final Long value, final boolean isSetTimeOut, final long liveTime, final TimeUnit timeUnit) {
        return new SessionCallback<Long>() {
            public Long execute(RedisOperations operations) throws DataAccessException {
                RedisForStringServiceImpl.this.startTransaction(key, operations);
                BoundValueOperations vOper = operations.boundValueOps(key);
                vOper.increment(value.longValue());
                if(isSetTimeOut) {
                    vOper.expire(liveTime, timeUnit);
                }

                List execResult = RedisForStringServiceImpl.this.commitTransaction(operations, key);
                Long afterIncValue = null;
                if(execResult.size() > 0) {
                    afterIncValue = (Long)execResult.get(0);
                }

                return afterIncValue;
            }
        };
    }

    private SessionCallback getSessionCallback(final String key) {
        return new SessionCallback() {
            public Object execute(RedisOperations operations) throws DataAccessException {
                BoundValueOperations vOper = operations.boundValueOps(key);
                return vOper.get();
            }
        };
    }

    private SessionCallback saveOrUpdateSessionCallback(final String key, final String value, final boolean isSetTimeOut, final long liveTime, final TimeUnit timeUnit) {
        return new SessionCallback() {
            public String execute(RedisOperations operations) throws DataAccessException {
                RedisForStringServiceImpl.this.startTransaction(key, operations);
                BoundValueOperations vOper = operations.boundValueOps(key);
                vOper.set(value);
                if(isSetTimeOut) {
                    vOper.expire(liveTime, timeUnit);
                }

                RedisForStringServiceImpl.this.commitTransaction(operations, key);
                return value;
            }
        };
    }

    private void startTransaction(String key, RedisOperations operations) {
        operations.watch(key);
        operations.multi();
    }

    private List commitTransaction(RedisOperations operations, String key) {
        List execResult = operations.exec();
        if(execResult == null) {
            this.logger.error("{}已经被改变，处理的事务被打断", key);
            throw new CacheException("[ " + key + " ]已经被改变，处理的事务被打断,没有成功");
        } else {
            this.logger.info("{}执行完之后的结果{}", key, execResult);
            return execResult;
        }
    }

    public RedisTemplate<String, ?> getRedisStringTemplate() {
        return this.redisTemplate;
    }

    public void setRedisStringTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public IKeyConfig getRedisKeyConfig() {
        if(this.redisKeyConfig == null) {
            RedisKeyConfigImpl redisKeyConfigImpl = (RedisKeyConfigImpl) RedisSpringApplicationContextHolder2.getBean("redisKeyConfig", RedisKeyConfigImpl.class);
            if(redisKeyConfigImpl == null) {
                throw new NoSuchBeanDefinitionException(RedisKeyConfigImpl.class, "RedisKeyConfigImpl没有初始化，请查看是否纳入spring管理");
            }

            this.redisKeyConfig = redisKeyConfigImpl;
        }

        return this.redisKeyConfig;
    }

    public void setRedisKeyConfig(IKeyConfig redisKeyConfig) {
        this.redisKeyConfig = redisKeyConfig;
    }
}
