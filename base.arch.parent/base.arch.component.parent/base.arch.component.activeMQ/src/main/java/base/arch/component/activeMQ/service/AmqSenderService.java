package basic.arch.component.activeMQ.service;

import java.util.Map;

import javax.jms.Destination;

public interface AmqSenderService {

	/** 
	 * @Description::(向指定的队列发送文本信息). <br/> 
	 * @author jingmiao
	 * @param destination
	 * @param message
	 */ 
	void sendMsg(Destination destination, String message);
	/** 
	 * @Description::(向指定的队列发送map信息). <br/> 
	 * @author jingmiao
	 * @param destination
	 * @param map
	 */ 
	void sendMap(Destination destination, Map<String,Object> map);
	/** 
	 * @Description::(向指定的队列发送序列化的bean信息). <br/> 
	 * @author jingmiao
	 * @param destination
	 * @param bean
	 */ 
	void sendBean(Destination destination, Object bean);
	
}
