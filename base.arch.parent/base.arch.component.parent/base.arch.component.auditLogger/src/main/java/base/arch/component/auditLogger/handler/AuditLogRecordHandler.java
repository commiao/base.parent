package base.arch.component.auditLogger.handler;

import java.util.TreeMap;

public interface AuditLogRecordHandler {

	public void doRecord(TreeMap<String, Object> treeMap);
}
