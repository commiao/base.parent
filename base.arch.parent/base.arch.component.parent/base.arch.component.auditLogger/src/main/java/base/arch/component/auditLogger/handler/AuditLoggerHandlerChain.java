package base.arch.component.auditLogger.handler;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.core.task.TaskExecutor;

import base.arch.component.auditLogger.stereotype.AuditLogInfo;
import base.arch.component.auditLogger.utils.AuditLoggerUtils;

public class AuditLoggerHandlerChain {

    private java.util.List<AuditLogRecordHandler> handlerList = new LinkedList<AuditLogRecordHandler>();

    public void handleAuditLog(AuditLogInfo auditLogInfo, TaskExecutor auditHandleExecutor) {
        if (auditLogInfo == null) {
            return;
        }
        TreeMap<String, Object> treeMap = AuditLoggerUtils.beanToMap(auditLogInfo);
        for (Iterator<AuditLogRecordHandler> iterator = handlerList.iterator(); iterator.hasNext();) {
            AuditLogRecordHandler auditLogRecordHandler = (AuditLogRecordHandler) iterator.next();
            if (auditHandleExecutor != null) {
                auditHandleExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        auditLogRecordHandler.doRecord(treeMap);
                    }
                });
            } else {
                auditLogRecordHandler.doRecord(treeMap);
            }
        }
    }

    public List<AuditLogRecordHandler> getHandlerList() {
        return handlerList;
    }

    public void setHandlerList(List<AuditLogRecordHandler> handlerList) {
        this.handlerList = handlerList;
    }
}
