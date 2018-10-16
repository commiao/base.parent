package base.arch.component.logger.web;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.LocalVariableTableParameterNameDiscoverer;

/**
 * @author jannal
 * @version 1.0
 * @date 2014-11-29
 */
public class LoggerUtils extends  org.springframework.util.ReflectionUtils{
    /**
     * 根据请求头数据判断是否是Ajax请求
     * @return
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader("X-Requested-With");
        if (header != null && "XMLHttpRequest".equals(header)) {
            return true;
        }
        return false;
    }
    
    
    public static Map<String,Object> beanToMap(Object... params) {
        Map<String, Object> param;
        try {
            param = new HashMap<String,Object>();
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
    
    /**
     * 获取ip地址
     * 
     * 
     * 像移动网关一样，iisforward这个ISAPI过滤器也会对request对象进行再包装，
     * 附加一些WLS要用的头信息。这种情况下，直接用request.getRemoteAddr()是无法取到真正的客户IP的。
                    实际的iisforward附加头如下：
     *  WL-Proxy-Client-IP=211.161.1.239  
        Proxy-Client-IP=211.161.1.239  
        X-Forwarded-For=211.161.1.239  
        WL-Proxy-Client-Keysize=   
        WL-Proxy-Client-Secretkeysize=   
        X-WebLogic-Request-ClusterInfo=true  
        X-WebLogic-KeepAliveSecs=30  
        X-WebLogic-Force-JVMID=-327089098  
        WL-Proxy-SSL=false  
     * 
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        if (request == null)
            return "";

        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (ip.equals("127.0.0.1")) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ip = inet.getHostAddress();
            }

        }

        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length() > 15) { // "***.***.***.***".length() = 15
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }

        return ip;
    }
    
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
}
