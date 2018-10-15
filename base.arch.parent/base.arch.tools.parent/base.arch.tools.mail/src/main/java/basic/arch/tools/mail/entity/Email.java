package basic.arch.tools.mail.entity;

import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import basic.arch.tools.mail.MailMessage;
import basic.arch.tools.mail.config.Configuration;

public class Email {
	
	static final Logger logger =LoggerFactory.getLogger(Email.class);
	
	protected static String port = Configuration.getPort();
	protected static String server = Configuration.getServer();//邮件服务器
	protected static String from = Configuration.getFrom();//发送者
	protected static String user = Configuration.getUser();//发送者地址
	protected static String password = Configuration.getPassword();//密码
	protected static Boolean istls = Configuration.getIstls();//默认不启用tls加密
	
	private static Email instance = null;
	private Email(){};
	public static final Email getInstance(){
		if(instance==null){
			instance = new Email();
		}
    	return instance;
    }
	/**
	 * TLS加密设置
	 * @param usingTLS
	 */
	private static Properties setTLS(Properties props) {
    	if(istls){
    		//设置tls加密
	        props.put("mail.smtp.starttls.enable", "true");  
	        //不做服务器证书校验  
	        props.put("mail.smtp.ssl.checkserveridentity", "false");  
	        //添加信任的服务器地址，多个地址之间用空格分开  
	        props.put("mail.smtp.ssl.trust", server);
    	}else{
    		props.remove("mail.smtp.starttls.enable");
            props.remove("mail.smtp.ssl.checkserveridentity");
            props.remove("mail.smtp.ssl.trust");
    	}
    	return props;
	}
	
	public boolean sendMail(MailMessage message){
		String msgSuject="";
		try {
			Properties props = new Properties();
            props.put("mail.smtp.host", server);
            props.put("mail.smtp.port", port);
            props.put("mail.smtp.auth", "true");
            //props.put( "mail.smtp.quitwait ", "false"); 
            props = setTLS(props);//设置tls加密
            Transport transport = null;
            Session session = Session.getInstance(props, null);
            transport = session.getTransport("smtp");
            transport.connect(server, user, password);
			MimeMessage msg = message.getMimeMessage(session, from);
            transport.sendMessage(msg, msg.getAllRecipients());
            
            msgSuject=msg.getSubject();
            if(logger.isDebugEnabled()){
				logger.debug("邮件发送成功"+msgSuject);
			}
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if(logger.isDebugEnabled()){
				logger.debug("邮件发送失败！"+msgSuject);
			}
        }
        return false;
	}
}
