package base.arch.component.auditLogger.interceptor;

import base.arch.component.auditLogger.stereotype.AuditLog;
import base.arch.component.auditLogger.stereotype.AuditLogInfo;

public abstract class AbstractAuditLogInterceptorImpl  implements AuditLogInterceptor{

    @Override
    public void beforeRecord(AuditLogInfo auditLogInfo, AuditLog al) {
        
    }

    @Override
    public void throwExceptionRecord(AuditLogInfo auditLogInfo, Exception e) {
        
    }

    @Override
    public void afterRecord(AuditLogInfo auditLogInfo) {
        
    }

}
