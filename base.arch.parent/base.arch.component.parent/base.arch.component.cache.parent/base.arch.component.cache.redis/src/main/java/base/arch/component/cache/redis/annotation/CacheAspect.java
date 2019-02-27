package base.arch.component.cache.redis.annotation;

import base.arch.component.cache.redis.handle.HandleBeanCacheTimer;
import base.arch.component.cache.redis.handle.HandleListCacheTimer;
import base.arch.component.cache.redis.handle.HandleMapCacheTimer;
import base.arch.component.cache.redis.util.JsonUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @CreateDate: 2018/11/27 15:27
 * @Description: TODO
 * @author: jingmiao
 * @version: V1.0
 */
@Aspect
@Component
public class CacheAspect {
    private static final Logger logger = LoggerFactory.getLogger(CacheAspect.class);

    @Pointcut("@annotation(base.arch.component.cache.redis.annotation.CacheAnnotation)")
    public void cut(){}

    @Around("cut() && @annotation(cacheAnnotation)")
    public Object around(ProceedingJoinPoint joinPoint, CacheAnnotation cacheAnnotation) throws Throwable {
        logger.info("=======================开始进入切面缓存处理==============start================");
        Object result = null;
        // 获取调用方法的参数
        Object[] args = joinPoint.getArgs();
        // 获取方法入参
        CacheAnnotationParam param = (CacheAnnotationParam)args[0];
        // 无法获取缓存key直接跳过
        if(param==null || (StringUtils.isBlank(param.getKey()) && StringUtils.isBlank(cacheAnnotation.prexKey()))){
            return joinPoint.proceed();
        }
        // 将注解参数赋值到param中
        updateByCacheAnnotation(param,cacheAnnotation);
        // 通过menthod获取方法名及返回对象
        updateByProceedingJoinPoint(param,joinPoint);
        if(param.getCacheType().isAssignableFrom(ArrayList.class)){
            result = getList(param,joinPoint);
        }else if(param.getCacheType().isAssignableFrom(HashMap.class)){
            result = getHashMap(param,joinPoint);
        }else{
            if(StringUtils.isBlank(param.getKey())){
                result = joinPoint.proceed();
                return result;
            }
            result = doObject(joinPoint, cacheAnnotation);
        }
        logger.info("=======================结束退出切面缓存处理==============end================");
        return result;
    }

    private void updateByCacheAnnotation(CacheAnnotationParam param ,CacheAnnotation annotation){
        param.setPrexKey(annotation.prexKey());
        param.setCacheType(annotation.cacheType());
        param.setTimeOut(annotation.timeOut());
        param.setTimeUnit(annotation.timeUnit());
        param.setDescription(annotation.description());
        param.setJsonPareType(annotation.jsonPareType());
    }

    private void updateByProceedingJoinPoint(CacheAnnotationParam param ,ProceedingJoinPoint joinPoint) throws IllegalAccessException, NoSuchMethodException, NoSuchFieldException {
        // 获取调用的方法
        Signature sig = joinPoint.getSignature();
        MethodSignature msig = null;
        if(!(sig instanceof MethodSignature)){
            throw new IllegalAccessException("该注解只能用于方法");
        }
        msig = (MethodSignature)sig;
        Method currentMethod = joinPoint.getTarget().getClass().getMethod(msig.getName(),msig.getParameterTypes());
        Type returnType = currentMethod.getGenericReturnType();
        if (returnType instanceof ParameterizedType) {
            List<Class<?>> returnBeanType = new ArrayList<>();
            Type[] typeArguments = ((ParameterizedType) returnType).getActualTypeArguments();
            for (Type type : typeArguments) {
                returnBeanType.add((Class<?>)type);
            }
            param.setReturnBeanClazz(returnBeanType);
        }
        param.setReturnClazz(currentMethod.getReturnType());
        param.setMethodName(currentMethod.getName());
    }

    private Object getList(CacheAnnotationParam param,ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        String key = StringUtils.isNotBlank(param.getPrexKey())?param.getPrexKey()+param.getKey():param.getKey();
        List list = null;
        Boolean saveStatus = false;
        if(param.isUseCache()) {
            if(param.getReturnBeanClazz()==null || param.getReturnBeanClazz().size()<1 || param.getReturnBeanClazz().get(0)==null){
                logger.error("使用缓存查询list:{}-{}接口-获取list泛型出现异常", param.getDescription(), param.getMethodName());
            }else {
                result = list = HandleListCacheTimer.getListCache(key, (Class<?>)param.getReturnBeanClazz().get(0));
                logger.info("使用缓存查询list:{}-{}接口-redis查询返回数据条数:{}", param.getDescription(), param.getMethodName(), list == null ? null : list.size());
            }
        }
        if(result==null){
            result = joinPoint.proceed();
            saveStatus = setList(result,param);
        }
        logger.info("更新缓存list:{}-{}接口-更新缓存状态saveStatus:{}-end",param.getDescription(),param.getMethodName(),saveStatus);
        return result;
    }

    public boolean setList(Object result,CacheAnnotationParam param){
        String key = StringUtils.isNotBlank(param.getPrexKey())?param.getPrexKey()+param.getKey():param.getKey();
        List list = (List)result;
        logger.info("更新缓存list:{}-{}接口-实时查询返回数据条数:{}",param.getDescription(),param.getMethodName(),list.size());
        return HandleListCacheTimer.setListCache(key, list, param.getTimeOut(), param.getTimeUnit(),true);
    }

    private Object getHashMap(CacheAnnotationParam param,ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        String key = StringUtils.isNotBlank(param.getPrexKey())?param.getPrexKey():param.getKey();
        String hashKey = param.getKey();
        Map map = null;
        if(param.isUseCache()) {
            if (param.getReturnClazz().isAssignableFrom(HashMap.class)) {
                result = map = HandleMapCacheTimer.getHashMap(key);
                logger.info("使用缓存查询map:{}-{}接口-redis查询返回数据条数:{}", param.getDescription(), param.getMethodName(), map==null?null:map.size());
            } else if (StringUtils.isNotBlank(hashKey)) {
                result = HandleMapCacheTimer.getHashBean(key, hashKey, param.getReturnClazz(), param.getJsonPareType());
                logger.info("使用缓存查询map:{}-{}接口-redis查询返回数据对象", param.getDescription(), param.getMethodName());
            }
        }
        if(result==null){
            result = joinPoint.proceed();
            setHashMap(result,param);
        }
        return result;
    }

    public void setHashMap(Object result,CacheAnnotationParam param){
        String key = StringUtils.isNotBlank(param.getPrexKey())?param.getPrexKey():param.getKey();
        String hashKey = param.getKey();
        Boolean saveStatus = false;
        Map map = null;
        if(param.getReturnClazz().isAssignableFrom(HashMap.class)) {
            map = (Map)result;
            logger.info("更新缓存map:{}-{}接口-实时查询返回数据条数:{}",param.getDescription(),param.getMethodName(),map.size());
            saveStatus = HandleMapCacheTimer.setHashMap(key, map, param.getDateFormat(), param.getTimeOut(), param.getTimeUnit());
        }else if(StringUtils.isNotBlank(hashKey)){
            logger.info("更新缓存map:{}-{}接口-实时查询返回数据对象",param.getDescription(),param.getMethodName());
            saveStatus = HandleMapCacheTimer.setHashBean(key,hashKey,result,param.getDateFormat());
        }
        logger.info("更新缓存map:{}-{}接口-更新缓存状态saveStatus:{}-end",param.getDescription(),param.getMethodName(),saveStatus);
    }

    public Object doObject(ProceedingJoinPoint joinPoint,CacheAnnotation cacheAnnotation) throws Throwable {
        Object result = null;
        // 注解缓存主键根路径
        String prexKey = cacheAnnotation.prexKey();
        // 注释缓存存储类型
        Class cacheType = cacheAnnotation.cacheType();
        // 注解描述
        String description = cacheAnnotation.description();
        // 注解超时时长
        long timeOut = cacheAnnotation.timeOut();
        // 注解超时单位
        TimeUnit timeUnit = cacheAnnotation.timeUnit();
        // 获取调用方法的参数
        Object[] args = joinPoint.getArgs();
        // 获取方法入参
        CacheAnnotationParam param = (CacheAnnotationParam)args[0];

        // 获取调用的方法
        Signature sig = joinPoint.getSignature();
        MethodSignature msig = null;
        if(!(sig instanceof MethodSignature)){
            throw new IllegalAccessException("该注解只能用于方法");
        }
        msig = (MethodSignature)sig;
        Method currentMethod = joinPoint.getTarget().getClass().getMethod(msig.getName(),msig.getParameterTypes());
        Boolean saveStatus = false;
        String key = StringUtils.isBlank(prexKey)?param.getKey():prexKey+param.getKey();
        logger.info("使用缓存查询bean:{}-{}接口-参数：key:{}-start",description,msig.getName(),key);
        result = param.isUseCache()?HandleBeanCacheTimer.getObjectCache(key, currentMethod.getReturnType()):null;
        if(result==null){
            result = joinPoint.proceed();
            if(result!=null) {
                saveStatus = HandleBeanCacheTimer.setObjectCache(key, result, param.getDateFormat(), timeOut, timeUnit);
            }
            logger.debug("使用缓存查询bean:{}-{}接口-实时查询返回数据:{}",description,msig.getName(),JsonUtils.bean2Json(result));
        }else{
            logger.debug("使用缓存查询bean:{}-{}接口-redis查询返回数据:{}",description,msig.getName(),JsonUtils.bean2Json(result));
        }
        logger.info("使用缓存查询bean:{}-{}接口-更新缓存状态saveStatus:{}-end",description,msig.getName(),saveStatus);
        return result;
    }

}
