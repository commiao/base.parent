package basic.arch.tools.mail;

import java.util.Date;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import basic.arch.tools.utils.StringUtils;


/**
 * 内部类，文本邮件内容
 */
public class TextMailMessage extends MailMessageSupport implements MailMessage{

	static final Logger logger =LoggerFactory.getLogger(TextMailMessage.class);
	
	private String subject; 
	private String body;
	
	public TextMailMessage() {
		super();
	}
	
	/**
	 * @param to 发送对象email地址
	 * @param subject 邮件标题
	 * @param body 邮件内容
	 */
	public TextMailMessage(String to, String subject, String body) {
		this(to, null, null, subject,body);
	}
	/**
	 * @param to 发送对象email地址
	 * @param cc 抄送对象email地址
	 * @param bcc 暗送对象email地址
	 * @param subject 邮件标题
	 * @param body 邮件内容
	 */
	public TextMailMessage(String to, String cc, String bcc, String subject,String body) {
		super();
		setTo(to);
		setCc(cc);
		setBcc(bcc);
		setSubject(subject);
		setBody(body);
	}
	
	/**
	 * @return 邮件标题
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * @param 邮件标题
	 */
	public TextMailMessage setSubject(String subject) {
		this.subject = subject;
		return this;
	}
	/**
	 * @return 邮件内容
	 */
	public String getBody() {
		return body;
	}
	/**
	 * @param 邮件内容
	 */
	public TextMailMessage setBody(String body) {
		this.body = body;
		return this;
	}
	
	public TextMailMessage addTo(String to){
		setTo(to);
		return this;
	}
	public TextMailMessage addCc(String cc){
		setCc(cc);
		return this;
	}
	public TextMailMessage addBcc(String bcc){
		setBcc(bcc);
		return this;
	}
	
	public MimeMessage getMimeMessage(Session session,String from) throws MessagingException{
		if(!hasTo() || subject == null){
			if(logger.isDebugEnabled()){
				logger.debug("to="+getTo()+",subject="+subject);
			}
			if(logger.isDebugEnabled()){
				logger.debug("没有发送地址或者没有主题，不能发送邮件！");
			}
			return null;
		}
		
		MimeMessage msg = new MimeMessage(session);
		msg.setSentDate(new Date());
		InternetAddress fromAddress = new InternetAddress(from);
		msg.setFrom(fromAddress);
		// 设定发送地址
		msg.setRecipients(Message.RecipientType.TO, getToAddress());
		// 设定抄送地址
		if (hasCc()) {
			msg.setRecipients(Message.RecipientType.CC, getCCAddress());
		}
		// 设定暗送地址
		if (hasBcc()) {
			msg.setRecipients(Message.RecipientType.BCC, getBCCAddress());
		}

		msg.setSubject(this.getSubject(), "UTF-8");
		msg.setText(StringUtils.replace(getBody(),"<br>","\r\n"), "UTF-8");
		msg.saveChanges();

		return msg;
	}

}
