package basic.arch.component.activeMQ.service;

import java.util.Map;

import javax.jms.Destination;
import javax.jms.TextMessage;

public interface AmqReceiveService{

	/** 
	 * @Description::(从指定的队列接收文本消息). <br/> 
	 * @author jingmiao
	 * @param destination
	 * @return
	 */ 
	TextMessage receive(Destination destination);
	
	/** 
	 * @Description::(从指定的队列接收map消息). <br/> 
	 * @author jingmiao
	 * @param destination
	 * @return
	 */ 
	Map<String,Object> receiveMap(Destination destination);
	
	/** 
	 * @Description::(从指定的队列接收序列化的bean消息). <br/> 
	 * @author jingmiao
	 * @param destination
	 * @param clazz
	 * @return
	 */ 
	<T>T receiveClazz(Destination destination,Class<T> clazz);
	
}
