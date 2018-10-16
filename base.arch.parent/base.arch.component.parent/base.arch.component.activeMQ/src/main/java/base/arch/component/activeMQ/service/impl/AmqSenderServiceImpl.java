package basic.arch.component.activeMQ.service.impl;

import java.io.Serializable;
import java.util.Map;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import basic.arch.component.activeMQ.service.AmqSenderService;

@Service("amqSenderService")
public class AmqSenderServiceImpl implements AmqSenderService{

    private static final Logger logger = LoggerFactory.getLogger(AmqSenderServiceImpl.class);

    @Resource(name = "jmsTemplate")
    private JmsTemplate jmsTemplate;


    /** 
     * 向指定的队列发送文本信息. 
     * @see basic.arch.component.activeMQ.service.AmqSenderService#sendMsg(javax.jms.Destination, java.lang.String) 
     */  
    @Override
    public void sendMsg(Destination destination, String message) {
        final String msg = JSON.toJSONString(message);
        try {
            logger.info("将要向队列{}发送的消息msg:{}", destination, msg);
            jmsTemplate.send(destination, new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    return session.createTextMessage(msg);
                }
            });

        } catch (Exception ex) {
            logger.error("向队列{}发送消息失败，消息为：{},异常信息为：{}", destination, msg, ex);
        }
    }

	/** 
	 * 向指定的队列发送map信息. 
	 * @see basic.arch.component.activeMQ.service.AmqSenderService#sendMap(javax.jms.Destination, java.util.Map) 
	 */  
	@Override
	public void sendMap(Destination destination, Map<String,Object> map) {
		try {
			logger.info("将要向队列{}发送的消息map:{}", destination.toString(), JSON.toJSONString(map));
			
			jmsTemplate.send(destination, new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    return session.createTextMessage(JSON.toJSONString(map));
                }
            });
		} catch (Exception ex) {
			logger.error("向队列{}发送消息失败，消息为：{},异常信息为：{}", destination, JSON.toJSON(map), ex);
		}
	}

	/** 
	 * 向指定的队列发送序列化的bean信息. 
	 * @see basic.arch.component.activeMQ.service.AmqSenderService#sendBean(javax.jms.Destination, java.lang.Object) 
	 */  
	@Override
	public void sendBean(Destination destination, Object bean){
		if(!(bean instanceof Serializable)){
			logger.error("向队列{}发送消息失败，消息为：{},原因为：{}", destination.toString(), JSON.toJSON(bean),"bean对象必须实现序列化");
			return;
		}
		try {
			logger.info("将要向队列{}发送的消息bean:{}", destination, JSON.toJSON(bean));
			jmsTemplate.send(destination, new MessageCreator() {
				
				@Override
				public Message createMessage(Session session) throws JMSException {
					return session.createObjectMessage((Serializable)bean);
				}
			});
		} catch (Exception ex){
			logger.error("向队列{}发送消息失败，消息为：{},异常信息为：{}", destination.toString(), JSON.toJSON(bean), ex);
		}
		
	}
}
