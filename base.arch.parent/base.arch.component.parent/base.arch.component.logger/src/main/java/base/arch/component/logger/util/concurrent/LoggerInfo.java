package base.arch.component.logger.util.concurrent;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

/**
 * CreateDate:2016年11月7日下午3:35:58
 * 
 * @Description: 审计日志信息
 * @author:jannal
 * @version V1.0
 */
public class LoggerInfo implements Serializable {
    /**
     */
    private static final long serialVersionUID = 1L;
    

    /**
     * 唯一的线程id
     */
    private String threadId;
    
    /**
     * 日志类型
     */
    private String type ="logger";

    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 服务器ip
     */
    private String serverIp;

    /**
     * 客户端ip
     */
    private String clientIp;

    /**
     * 用户代理
     */
    private String userAgent;
    /**
     * sessionId
     */
    private String sessionId;
    /**
     * 请求URl
     */
    private String requsetURL;
    /**
     * 请求参数
     */
    private String userInfo;
    /**
     * 请求参数
     */
    private String requestParams;
    /**
     * 响应数据
     */
    private String responseData;

    /**
     * 结束时间
     */
    private String endTime;
    

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getRequsetURL() {
        return requsetURL;
    }

    public void setRequsetURL(String requsetURL) {
        this.requsetURL = requsetURL;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public String getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(String requestParams) {
        this.requestParams = requestParams;
    }

    public String getResponseData() {
        return responseData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
