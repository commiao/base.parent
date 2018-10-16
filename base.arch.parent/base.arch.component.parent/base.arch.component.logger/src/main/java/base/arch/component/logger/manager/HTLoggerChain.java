package base.arch.component.logger.manager;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import base.arch.component.logger.handler.ILoggerHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import base.arch.component.logger.bean.Group;

/**
 * 日志处理链
 * @author jannal
 */
public class HTLoggerChain {
    private static final String DEFAULT_GROUP = "defaultGroup";
    
    private Logger logger =LoggerFactory.getLogger(HTLoggerChain.class);
    private Map<String,LinkedList<ILoggerHandler>>  map= new HashMap<String,LinkedList<ILoggerHandler>>();

    private Group defaultGroup;
    
    public HTLoggerChain(Map<String, LinkedList<ILoggerHandler>> map) {
        this.map = map;
    } 
    
    public void handleRequest(Group group){
        if(group==null){
            group = defaultGroup;
        }
        if(StringUtils.isEmpty(group.getName())){
            group.setName(defaultGroup.getName());
        }
        LinkedList<ILoggerHandler> linkedList = map.get(group.getName());
        if(linkedList==null||linkedList.size()==0){
           //调用默认的记录
            linkedList= map.get(DEFAULT_GROUP);
            group.setName(defaultGroup.getName());
            group.setDesc(defaultGroup.getDesc());
        }
        int size = linkedList.size();
        ILoggerHandler iLoggerHandler = null;
        for(int i=0;i<size;i++){
            iLoggerHandler = linkedList.get(i);
            try {
                iLoggerHandler.handle(group);
            } catch (Exception e) {
                logger.error("日志处理发生错误",e);
                throw new RuntimeException(e.getMessage(),e);
            }
        }
    }

    public Group getDefaultGroup() {
        return defaultGroup;
    }

    public void setDefaultGroup(Group defaultGroup) {
        this.defaultGroup = defaultGroup;
    }
    
}
