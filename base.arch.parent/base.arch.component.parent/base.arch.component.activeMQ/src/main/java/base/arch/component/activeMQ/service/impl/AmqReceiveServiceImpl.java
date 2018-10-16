package basic.arch.component.activeMQ.service.impl;

import java.util.Map;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import basic.arch.component.activeMQ.service.AmqReceiveService;

@Service("amqReceiveService")
public class AmqReceiveServiceImpl implements AmqReceiveService{

    private static final Logger logger = LoggerFactory.getLogger(AmqSenderServiceImpl.class);

    @Resource(name = "jmsTemplate")
    private JmsTemplate jmsTemplate;
    
    private Message getMessage(Destination destination){
    	return jmsTemplate.receive(destination);
    }
    
    /** 
     * 从指定的队列接收文本消息. 
     * @see basic.arch.component.activeMQ.service.AmqReceiveService#receive(javax.jms.Destination) 
     */  
    @Override
	public TextMessage receive(Destination destination) {
    	TextMessage tm = null;
    	Message message = getMessage(destination);
    	if(message instanceof TextMessage){
    		tm = (TextMessage) message;
    		if (tm != null) {
    			try {
    				logger.info("Receive Message:{},from Destination{}。",tm.getText(),destination.toString());
    			} catch (JMSException e) {
    				logger.error("从队列{}接收消息失败,异常信息为：{}", destination, e );
    			}
    		}
    	}else{
    		logger.error("只能接收【文本】类型消息");
    	}
    	return tm;
	}
    
    /** 
     * 从指定的队列接收map消息. 
     * @see basic.arch.component.activeMQ.service.AmqReceiveService#receiveMap(javax.jms.Destination) 
     */  
    @Override
    public Map<String,Object> receiveMap(Destination destination){
    	Map<String,Object> map = null;
    	TextMessage tm = null;
    	Message message = getMessage(destination);
    	if(message instanceof TextMessage){
    		tm = (TextMessage) message;
    		if (tm != null) {
    			try {
    				logger.info("Receive Message:{},from Destination{}。",tm.getText(),destination.toString());
    				map = JSON.parseObject(tm.getText()); 
    			} catch (JMSException e) {
    				logger.error("从队列{}接收消息失败,异常信息为：{}", destination, e );
    			}
    		}
    	}else{
    		logger.error("只能接收【文本】类型消息");
    	}
    	return map;
    }
    
    /** 
     * 从指定的队列接收序列化的bean消息. 
     * @see basic.arch.component.activeMQ.service.AmqReceiveService#receiveClazz(javax.jms.Destination, java.lang.Class) 
     */  
    @Override
    public <T>T receiveClazz(Destination destination,Class<T> clazz){
    	ObjectMessage om = null;
    	Message message = getMessage(destination);
    	T t = null;
    	if(message instanceof ObjectMessage){
    		om = (ObjectMessage)message;
    		if(om!=null){
    			try {
    				logger.info("Receive Message:{},from Destination{}。",JSON.toJSON(om.getObject()),destination.toString());
					t = clazz.cast(om.getObject());
				} catch (JMSException e) {
					logger.error("从队列{}接收消息失败,异常信息为：{}", destination, e);
				}
    		}
    	}else{
    		logger.error("只能接收【实现serializable接口】类型消息");
    	}
    	return t;
    }
}
