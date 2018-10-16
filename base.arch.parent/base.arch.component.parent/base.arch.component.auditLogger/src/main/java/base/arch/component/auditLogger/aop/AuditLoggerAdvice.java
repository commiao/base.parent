package base.arch.component.auditLogger.aop;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import base.arch.component.auditLogger.manager.AuditLoggerManager;
import base.arch.component.auditLogger.stereotype.AuditLog;
import base.arch.component.auditLogger.stereotype.AuditLogInfo;
import base.arch.component.auditLogger.utils.AuditLoggerUtils;
public class AuditLoggerAdvice {

    private AuditLoggerManager auditLoggerManager;
    public Object logAround(ProceedingJoinPoint pjp, AuditLog auditLog) throws Throwable {
        Object returnVal = null;
        AuditLogInfo  auditLogInfo = new AuditLogInfo();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss.SSS");
        Date startDate = new Date();
        String startTime = df.format(startDate);
        auditLogInfo.setOperationStartTime(startTime);
        try {
            Method method = doBefore(pjp, auditLog, auditLogInfo);
            getResult(pjp, returnVal, method);
            doAfter(auditLog, returnVal, auditLogInfo, method);
        } catch (Exception e) {
            throw throwException(e, auditLogInfo);
        } finally {
            Date endDate = new Date();
            String endTime = df.format(endDate);
            long operationTime =  endDate.getTime()-startDate.getTime();
            auditLogInfo.setOperationEndTime(endTime);
            auditLogInfo.setOperationTime(operationTime+"毫秒");
            if (auditLoggerManager != null) {
                auditLoggerManager.accept(auditLogInfo, auditLog);
            }
        }
        return returnVal;
    }


    private void doAfter(AuditLog auditLog, Object returnVal, AuditLogInfo auditLogInfo, Method method) {
        if(auditLog.isRecordReturn()&&!AuditLoggerUtils.isVoidReturnType(method)){
           auditLogInfo.setReturnVal(returnVal);
        }
    }


    private Method doBefore(ProceedingJoinPoint pjp, AuditLog auditLog, AuditLogInfo auditLogInfo) {
        Object[] argsValue = pjp.getArgs();// 具体的参数值
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();//方法
        Class<?>[] argsClass = method.getParameterTypes();// 参数类型
        Class<? extends Object> targetClass = pjp.getTarget().getClass();//目标类
        String targetName = targetClass.getName();
         
        if(auditLog.isRecordArgs()){
            String[] methodParameterNames = AuditLoggerUtils.getMethodParameterNames(targetClass, argsClass, method);
            LinkedHashMap<String, Object> argsMap =new LinkedHashMap<String, Object> ();
            for(int i=0;i<methodParameterNames.length;i++){
                argsMap.put(methodParameterNames[i], argsValue[i]);
            }
            auditLogInfo.setArgs(argsMap);
        }
        auditLogInfo.setMethod(targetName+":"+method.getName());
        return method;
    }


    private Exception throwException(Exception e, AuditLogInfo auditLogInfo) {
        auditLogInfo.setException(e);
        return e;
    }


    public AuditLoggerManager getAuditLoggerManager() {
        return auditLoggerManager;
    }

    public void setAuditLoggerManager(AuditLoggerManager auditLoggerManager) {
        this.auditLoggerManager = auditLoggerManager;
    }
    
    protected Object getResult(ProceedingJoinPoint pjp, Object result, Method method) throws Throwable {
        if (AuditLoggerUtils.isVoidReturnType(method)) {
            pjp.proceed();
        } else {
            result = pjp.proceed();
        }
        return result;
    }

}
