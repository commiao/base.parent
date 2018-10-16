package basic.arch.component.activeMQ;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.jms.Destination;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import basic.arch.component.activeMQ.entity.User;
import basic.arch.component.activeMQ.service.AmqSenderService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext-all.xml" })
public class AmqSenderServiceTest{

    //目的地队列的明证，我们要向这个队列发送消息
    @Resource(name = "destinationQueue")
    private Destination destination;
	
	@Autowired
	private AmqSenderService amqSenderService;
	
	@Before
	public void start(){
		System.out.println("======================单元测试开始======================");
	}
	
	@Test
	public void testsendMsg() throws InterruptedException{
		for(int i=6;i<11;i++){
			Thread.sleep(1000);
			amqSenderService.sendMsg(destination, "测试MQ队列########################+"+i);
		}
		System.out.println("执行发送信息完成");
	}
	
	@Test
	public void testsendMap(){
		Map<String,Object> map = new HashMap<>();
		for(int j=0;j<5;j++){
			for(int i = 0;i<5;i++){
				map.put(i+"", "测试"+i);
			}
			amqSenderService.sendMap(destination, map);
		}
	}
	
	@Test
	public void testsendBean(){
		User user = null;
		for(int i = 0;i<5;i++){
			user = new User();
			user.setId(Long.valueOf(i));
			user.setAge(i);
			user.setName("测试名字+"+i);
			amqSenderService.sendBean(destination, user);
		}
	}
	
	@After
	public void end(){
		System.out.println("======================单元测试结束======================");
	}
}
