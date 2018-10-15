package basic.arch.tools.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext-all.xml")
public class ConfigBeanTest {

	
	@Autowired
	private ConfigBean configBean;
	
	@Test
	public void start(){
		System.out.println("单元测试开始");
	}
	
	@Test
	public void test(){
		String str = configBean.getPofSmsConfig().getSmsUrl();
		System.out.println("测试结果========================="+str);
	}
	
	@Test
	public void end(){
		System.out.println("单元测试结束");
	}
	
	
}
