package basic.arch.tools.utils.servlet;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jannal
 * @version 1.0
 * @date 2014-11-29
 */
public class ServletUtils {
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
}
