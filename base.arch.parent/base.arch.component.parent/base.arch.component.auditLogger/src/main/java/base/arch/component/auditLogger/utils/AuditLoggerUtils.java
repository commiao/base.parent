package base.arch.component.auditLogger.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.core.LocalVariableTableParameterNameDiscoverer;

public class AuditLoggerUtils {
    /**
     * 判断是否有返回值
     * @param method
     * @return
     */
    public static boolean isVoidReturnType(Method method){
        if(void.class.equals(method.getReturnType())){
            return true;
        }
        return false;
    }
    /**
     * @param targetClass 目标类
     * @param args  目标方法的参数
     * @param method 目标方法
     * @return
     */
    public static String[] getMethodParameterNames(Class<? extends Object> targetClass,Class<?>[] args, Method method){
        String[] parameterNames;
        try {
            LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
            Method declaredMethod = targetClass.getDeclaredMethod(method.getName(), args);
            parameterNames = u.getParameterNames(declaredMethod);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (SecurityException e) {
            throw new RuntimeException(e);
        }
        return parameterNames;
    }
    
    public static TreeMap<String,Object> beanToMap(Object... params) {
        TreeMap<String, Object> param;
        try {
            param = new TreeMap<String,Object>();
            for(Object obj:params){
                Class<?> clazz = obj.getClass();
                Method[] methods = clazz.getMethods();
                Pattern pattern = Pattern.compile("^get*");
                Matcher matcher = null;
                for(Method m:methods){
                    matcher = pattern.matcher(m.getName());
                        if(matcher.find()){
                            String key = m.getName().replace("get", "");
                            key = (key.charAt(0)+"").toLowerCase().concat(key.substring(1));
                            if("class".equals(key)){
                                continue;
                            }
                            Object res = m.invoke(obj);
                            if(res == null){
                                continue;
                            }
                            param.put(key, res);
                        }
                }
            }
        } catch (SecurityException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return param;
    }
}
