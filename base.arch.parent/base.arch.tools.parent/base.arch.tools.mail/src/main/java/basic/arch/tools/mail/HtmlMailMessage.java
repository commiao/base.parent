package basic.arch.tools.mail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import basic.arch.tools.utils.StringUtils;

/**
 * 内部类，网页格式邮件内容
 */
public class HtmlMailMessage extends MailMessageSupport implements MailMessage{
	
	static final Logger logger =LoggerFactory.getLogger(HtmlMailMessage.class);
	
	private String subject; 
	private String body;
	private List<String> attachList;
	
	public HtmlMailMessage() {
		super();
	}
	
	/**
	 * @param to 发送对象email地址
	 * @param subject 邮件标题
	 * @param body 邮件内容
	 */
	public HtmlMailMessage(String to, String subject, String body) {
		this(to, null, null, subject,body);
	}
	/**
	 * @param to 发送对象email地址
	 * @param cc 抄送对象email地址
	 * @param bcc 暗送对象email地址
	 * @param subject 邮件标题
	 * @param body 邮件内容
	 * @param attachs 邮件附件
	 */
	public HtmlMailMessage(String to, String cc, String bcc, String subject,String body,String... attachs) {
		this(to, cc, bcc, subject,body);
		if(attachs != null && attachs.length>0){
			for(int i=0;i<attachs.length;i++){
				addAttach(attachs[i]);
			}
		}
	}
	/**
	 * @param to 发送对象email地址
	 * @param cc 抄送对象email地址
	 * @param bcc 暗送对象email地址
	 * @param subject 邮件标题
	 * @param body 邮件内容
	 */
	public HtmlMailMessage(String to, String cc, String bcc, String subject,String body) {
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
	public HtmlMailMessage setSubject(String subject) {
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
	 * @return 邮件附件列表
	 */
	public List<String> getAttachs(){
		return attachList;
	}
	/**
	 * @param 邮件附件名称
	 */
	public HtmlMailMessage addAttach(String fileName){
		if(attachList==null){
			attachList = new ArrayList<String>();
		}
		attachList.add(fileName);
		return this;
	}
	/**
	 * @param 邮件内容
	 */
	public HtmlMailMessage setBody(String body) {
		this.body = body;
		return this;
	}
	
	public HtmlMailMessage addTo(String to){
		setTo(to);
		return this;
	}
	public HtmlMailMessage addCc(String cc){
		setCc(cc);
		return this;
	}
	public HtmlMailMessage addBcc(String bcc){
		setBcc(bcc);
		return this;
	}
	
	public MimeMessage getMimeMessage(Session session,String from) throws MessagingException{
		if(!hasTo() || subject == null || body == null){
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
		
        Multipart mp = new MimeMultipart();

        MimeBodyPart mbpContent = new MimeBodyPart();
        //mbpContent.setText(this.getBody(), "UTF-8");
        mbpContent.setContent(StringUtils.replace(getBody(),"\r\n", "<br>"),"text/html;charset=UTF-8");    
        
        mp.addBodyPart(mbpContent);

        //添加附件
        if(attachList != null && !attachList.isEmpty()){
        	for(String fileName : attachList){
                MimeBodyPart mbpFile = new MimeBodyPart();
                FileDataSource fds = new FileDataSource(fileName);
                mbpFile.setDataHandler(new DataHandler(fds));
                mbpFile.setFileName(fds.getName());
                //向MimeMessage添加（Multipart代表附件）
                mp.addBodyPart(mbpFile);
        	}

        }
        //向Multipart添加MimeMessage
        msg.setContent(mp);
        msg.saveChanges();
		
		return msg;
	}

}
