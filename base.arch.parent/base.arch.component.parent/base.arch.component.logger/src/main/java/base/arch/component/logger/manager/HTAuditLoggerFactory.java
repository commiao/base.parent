package base.arch.component.logger.manager;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import base.arch.component.logger.handler.HTLoggerImpl;
import base.arch.component.logger.bean.Group;
import base.arch.component.logger.bean.JarInfo;
import base.arch.component.logger.bean.WarInfo;
import base.arch.component.logger.handler.HTLogger;

/**
 * 日志工厂
 * 
 * @author jannal
 */
public class HTAuditLoggerFactory extends AbstrctLoggerFactory{

    private static final ConcurrentMap<String, Group> LOGGERS = new ConcurrentHashMap<String, Group>();

    private HTAuditLoggerFactory() {
    }

    public static HTLogger getLogger(Class<?> key) {
        Group group = LOGGERS.get(key.getName());
        if (group == null) {
            LOGGERS.putIfAbsent(key.getName(), new Group(key.getName()));
            group = LOGGERS.get(key.getName());
        }
        group.setType("audit");
        JarInfo jarInfo = getJarInfo(key);
        group.setJarInfo(jarInfo);
        HTLogger logger = new HTLoggerImpl(group);
        return logger;
    }
    
    public static HTLogger getLoggerWar(Class<?> key,WarInfo warInfo) {
        Group group = LOGGERS.get(key.getName());
        if (group == null) {
            LOGGERS.putIfAbsent(key.getName(), new Group(key.getName()));
            group = LOGGERS.get(key.getName());
        }
        group.setType("audit");
        group.setWarInfo(warInfo);
        group.setPackageType("war");
        HTLogger logger = new HTLoggerImpl(group);
        return logger;
    }


    public static HTLogger getLogger(String key) {
        Group group = LOGGERS.get(key);
        if (group == null) {
            LOGGERS.putIfAbsent(key, new Group(key));
            group = LOGGERS.get(key);
        }
        group.setType("audit");
        Class<?> forName;
        JarInfo jarInfo=null;
        try {
            forName = Class.forName(key);
            jarInfo = getJarInfo(forName);
        } catch (ClassNotFoundException e) {
        }
        group.setJarInfo(jarInfo);
        HTLogger logger = new HTLoggerImpl(group);
        return logger;
    }
    


}
