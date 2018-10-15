package basic.arch.tools.mail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import basic.arch.tools.mail.entity.Email;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext-all.xml")
public class MailUtilsTest {
	@Before
	public void setUp() throws Exception {
		System.out.println("before");
	}

	@After
	public void testdown() {
		System.out.println("end");
	}
	
//	@Test
    public void testsendTextMail() {
	    MailUtils.sendTextMail("jingmiao0924@163.com", "测试003", "测试成功");
	}
	
	@Test
	public void testEmail(){
		TextMailMessage text = new TextMailMessage("jingmiao0924@163.com", "测试004", "测试成功");
		Email.getInstance().sendMail(text);
	}
}
