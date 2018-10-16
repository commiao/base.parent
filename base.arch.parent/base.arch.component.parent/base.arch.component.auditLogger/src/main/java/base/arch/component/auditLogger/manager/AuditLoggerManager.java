package base.arch.component.auditLogger.manager;

import org.springframework.core.task.TaskExecutor;

import base.arch.component.auditLogger.handler.AuditLoggerHandlerChain;
import base.arch.component.auditLogger.interceptor.AuditLogInterceptor;
import base.arch.component.auditLogger.stereotype.AuditLog;
import base.arch.component.auditLogger.stereotype.AuditLog.RecordStrategy;
import base.arch.component.auditLogger.stereotype.AuditLogInfo;

public class AuditLoggerManager {

	/**
	 * 异步处理线程池
	 */
	private TaskExecutor auditHandleExecutor;

	/**
	 * 审计日志处理链
	 */
	private  AuditLoggerHandlerChain auditLoggerHandlerChain ;

	/**
	 * 拦截器
	 */
	private AuditLogInterceptor auditLogInterceptor;

	public void accept(AuditLogInfo auditLogInfo, AuditLog al) {

		try {
			if (auditLogInterceptor != null) {
				auditLogInterceptor.beforeRecord(auditLogInfo, al);
			}
			if(RecordStrategy.ALL.equals(al.recordStrategy())){
			    if (auditLoggerHandlerChain != null) {
                    auditLoggerHandlerChain.handleAuditLog(auditLogInfo,auditHandleExecutor);
                }
			}else if(RecordStrategy.EXCEPTION.equals(al.recordStrategy())&&auditLogInfo.getException()!=null ){
			    if (auditLoggerHandlerChain != null) {
                    auditLoggerHandlerChain.handleAuditLog(auditLogInfo,auditHandleExecutor);
                }
			}

		} catch (Exception e) {
			if (auditLogInterceptor != null) {
			    auditLogInfo.setException(e);
				auditLogInterceptor.throwExceptionRecord(auditLogInfo, e);
			}
		} finally {
			if (auditLogInterceptor != null) {
				auditLogInterceptor.afterRecord(auditLogInfo);
			}
		}

	}

    public void setAuditLogInterceptor(AuditLogInterceptor auditLogInterceptor) {
        this.auditLogInterceptor = auditLogInterceptor;
    }

	public AuditLogInterceptor getAuditLogInterceptor() {
        return auditLogInterceptor;
    }

    public TaskExecutor getAuditHandleExecutor() {
		return auditHandleExecutor;
	}

	public void setAuditHandleExecutor(TaskExecutor auditHandleExecutor) {
		this.auditHandleExecutor = auditHandleExecutor;
	}

    public AuditLoggerHandlerChain getAuditLoggerHandlerChain() {
        return auditLoggerHandlerChain;
    }

    public void setAuditLoggerHandlerChain(AuditLoggerHandlerChain auditLoggerHandlerChain) {
        this.auditLoggerHandlerChain = auditLoggerHandlerChain;
    }

}
