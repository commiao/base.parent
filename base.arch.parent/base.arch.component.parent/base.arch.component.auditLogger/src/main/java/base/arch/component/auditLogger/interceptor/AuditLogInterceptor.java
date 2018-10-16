package base.arch.component.auditLogger.interceptor;

import base.arch.component.auditLogger.stereotype.AuditLog;
import base.arch.component.auditLogger.stereotype.AuditLogInfo;

public interface AuditLogInterceptor {

	public void beforeRecord(AuditLogInfo auditLogInfo,AuditLog al);
	
	public void throwExceptionRecord(AuditLogInfo auditLogInfo,Exception e);
	
	public void afterRecord(AuditLogInfo auditLogInfo);
}
