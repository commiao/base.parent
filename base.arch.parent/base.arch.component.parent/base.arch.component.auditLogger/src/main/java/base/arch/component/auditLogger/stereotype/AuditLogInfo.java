package base.arch.component.auditLogger.stereotype;

import java.util.LinkedHashMap;

import com.alibaba.fastjson.JSONObject;

public class AuditLogInfo {
    
    /**
     * 日志类型
     */
    private String type ="audit";
    /**
     * 调用的方法
     */
    private String method;
    /**
     * 返回值
     */
    private Object returnVal;
    /**
     * 方法参数
     */
    private LinkedHashMap<String, Object> args;
    /**
     * 异常信息
     */
    private Exception exception;
    
    /**
     * 操作起始时间
     */
    private String operationStartTime;
    
    /**
     * 操作结束时间
     */
    private String operationEndTime;

    /**
     * 操作用时
     */
    private String operationTime;


    public Object getReturnVal() {
        return returnVal;
    }

    public void setReturnVal(Object returnVal) {
        this.returnVal = returnVal;
    }

    public LinkedHashMap<String, Object> getArgs() {
        return args;
    }

    public void setArgs(LinkedHashMap<String, Object> args) {
        this.args = args;
    }

    
    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public String getOperationStartTime() {
        return operationStartTime;
    }

    public void setOperationStartTime(String operationStartTime) {
        this.operationStartTime = operationStartTime;
    }

    public String getOperationEndTime() {
        return operationEndTime;
    }

    public void setOperationEndTime(String operationEndTime) {
        this.operationEndTime = operationEndTime;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

    public String getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(String operationTime) {
        this.operationTime = operationTime;
    }

    public String getType() {
        return type;
    }
    
}
