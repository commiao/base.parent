package base.arch.component.logger.handler;

import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.beans.BeanInstantiationException;

import base.arch.component.logger.bean.Group;
import base.arch.component.logger.bean.HTLevel;
import base.arch.component.logger.manager.HTLoggerManager;
import base.arch.component.logger.manager.LoggerSpringApplicationContextHolder;

/**
 * logger实现类
 * @author jannal
 */
public class HTLoggerImpl implements HTLogger{

    private static final long serialVersionUID = 1L;
    
    private static HTLoggerManager loggerManager;
    
    private final  Group _group;

    public HTLoggerImpl(final Group group) {
       this._group =group;
    }
    
    private String getLoggerMessage(String msg) {
        //LoggerInfo loggerInfo = LoggerThreadContext.get();
     /*   HashMap<String, Object> hashMap = new HashMap<>(2);
        hashMap.put("threadId", loggerInfo.getThreadId());
        hashMap.put("message", msg);*/
       /* msg = JSONObject.toJSONString(hashMap);*/
        return msg;
    }
    
    @Override
    public void debug(Throwable e) {
        Group group = getCloneGroup();
        group.setLevel(HTLevel.DEBUG);
        group.setThrowable(e);
        String loggerMessage = getLoggerMessage(null);
        group.setMessage(loggerMessage);
        getLoggerManager().execute(group);
    }

    @Override
    public void debug(String msg, Throwable e) {
        Group group = getCloneGroup();
        group.setLevel(HTLevel.DEBUG);
        msg = getLoggerMessage(msg);
        group.setMessage(msg);
        group.setThrowable(e);
        getLoggerManager().execute(group);
    }


    
    public void debug(String format, Object... arguments){
        FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
        Group group = getCloneGroup();
        group.setLevel(HTLevel.DEBUG);
        String msg = getLoggerMessage(ft.getMessage());
        group.setMessage(msg);
        group.setThrowable(ft.getThrowable());
        getLoggerManager().execute(group);
    }
    
    
    @Override
    public void error(Throwable e) {
        Group group = getCloneGroup();
        group.setLevel(HTLevel.ERROR);
        group.setThrowable(e);
        String loggerMessage = getLoggerMessage(null);
        group.setMessage(loggerMessage);
        getLoggerManager().execute(group);
    }

    private Group getCloneGroup() {
        Group group = new Group(_group.getClassName());
        group.setType(_group.getType());
        group.setJarInfo(_group.getJarInfo());
        group.setPackageType(_group.getPackageType());
        return group;
    }

    @Override
    public void error(String msg, Throwable e) {
        Group group = getCloneGroup();
        group.setLevel(HTLevel.ERROR);
        msg = getLoggerMessage(msg);
        group.setMessage(msg);
        group.setThrowable(e);
        getLoggerManager().execute(group);
    }
    
    public void error(String format, Object... arguments){
        Group group = getCloneGroup();
        FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
        group.setLevel(HTLevel.ERROR);
        String msg = getLoggerMessage(ft.getMessage());
        group.setMessage(msg);
        group.setThrowable(ft.getThrowable());
        getLoggerManager().execute(group);
    }
    

    @Override
    public void info(String msg) {
        Group group = getCloneGroup();
        group.setLevel(HTLevel.INFO);
        msg = getLoggerMessage(msg);
        group.setMessage(msg);
        getLoggerManager().execute(group);
    }

    @Override
    public void info(Throwable e) {
        Group group = getCloneGroup();
        group.setLevel(HTLevel.INFO);
        group.setThrowable(e);
        String loggerMessage = getLoggerMessage(null);
        group.setMessage(loggerMessage);
        getLoggerManager().execute(group);
    }

    @Override
    public void info(String msg, Throwable e) {
        Group group = getCloneGroup();
        group.setLevel(HTLevel.INFO);
        msg = getLoggerMessage(msg);
        group.setMessage(msg);
        group.setThrowable(e);
        getLoggerManager().execute(group);
    }
    
    public void info(String format, Object... arguments){
        Group group = getCloneGroup();
        FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
        group.setLevel(HTLevel.INFO);
        String msg = getLoggerMessage(ft.getMessage());
        group.setMessage(msg);
        group.setThrowable(ft.getThrowable());
        getLoggerManager().execute(group);
    }
    @Override
    public void warn(String msg) {
        Group group = getCloneGroup();
        group.setLevel(HTLevel.WARN);
        msg = getLoggerMessage(msg);
        group.setMessage(msg);
        getLoggerManager().execute(group);
    }

    @Override
    public void warn(Throwable e) {
        Group group = getCloneGroup();
        group.setLevel(HTLevel.WARN);
        group.setThrowable(e);
        String loggerMessage = getLoggerMessage(null);
        group.setMessage(loggerMessage);
        getLoggerManager().execute(group);
    }

    @Override
    public void warn(String msg, Throwable e) {
        Group group = getCloneGroup();
        group.setLevel(HTLevel.WARN);
        msg = getLoggerMessage(msg);
        group.setMessage(msg);
        group.setThrowable(e);
        getLoggerManager().execute(group);
    }
    

    @Override
    public void warn(String format, Object... arguments) {
        FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
        Group group = getCloneGroup();
        group.setLevel(HTLevel.WARN);
        String msg = getLoggerMessage(ft.getMessage());
        group.setMessage(msg);
        group.setThrowable(ft.getThrowable());
        getLoggerManager().execute(group);
    }
    
    
    
    

    public  HTLoggerManager getLoggerManager() {
        if(loggerManager==null){
           loggerManager = (HTLoggerManager)LoggerSpringApplicationContextHolder.getBean("loggerManager");
           if(loggerManager==null){
               throw new BeanInstantiationException(HTLoggerManager.class,"检查loggerManager是否正常初始化");
           }
        }
        return loggerManager;
    }

    public  void setLoggerManager(HTLoggerManager loggerManager) {
        HTLoggerImpl.loggerManager = loggerManager;
    }


   
    
    
  
}
