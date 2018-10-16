package basic.arch.component.activeMQ;

import java.util.Map;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.TextMessage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;

import basic.arch.component.activeMQ.entity.User;
import basic.arch.component.activeMQ.service.AmqReceiveService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext-all.xml" })
public class AmqReceiveServiceTest {

    //目的地队列的明证，我们要向这个队列发送消息
    @Resource(name = "destinationQueue")
    private Destination destination;
	
	@Autowired
	private AmqReceiveService amqReceiveService;
	
	@Test
	public void testreceive() throws InterruptedException{
		TextMessage tm = null;
		while(true){
			tm = amqReceiveService.receive(destination);
			if(tm==null){
				break;
			}
		}
		System.out.println("执行接收信息完成");
	}	
	
	@Test
	public void testreceiveMap(){
		Map<String,Object> map = null;
		while(true){
			map = amqReceiveService.receiveMap(destination);
			if(map == null){
				break;
			}
		}
		System.out.println("执行接收信息完成");
	}
	
	@Test
	public void testreceiveClazz(){
		User user = null;
		while(true){
			user = amqReceiveService.receiveClazz(destination, User.class);
			System.out.println(JSON.toJSON(user));
			if(user==null){
				break;
			}
		}
		System.out.println("执行接收信息完成");
	}
	
	public static User getcheck(int i){
		User user = new User();
		user.setAge(i);
		user.setId(Long.valueOf(i));
		user.setName("测试"+i);
		return user;
	}
	
	public static void main(String[] args) {
		User user = null;
		for(int i=0;i<10;i++){
			user = getcheck(i);
			user.setAge(user.getAge()+10);
			System.out.println(JSON.toJSONString(user));
		}
	}
	
}
