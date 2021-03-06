package base.arch.tools.mail;

import base.arch.tools.mail.config.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import base.arch.tools.config.ConfigBean;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext-all.xml")
public class ConfigTest {
	
	@Autowired
	private ConfigBean configBean;
	
	@Before
	public void setUp() throws Exception {
		System.out.println("before");
	}

	@After
	public void testdown() {
		System.out.println("end");
	}
	
	@Test
	public void testEmail(){
		System.out.println(Configuration.getPort());
		System.out.println(configBean.getMailConfig().getMailServerPort());
	}
}
