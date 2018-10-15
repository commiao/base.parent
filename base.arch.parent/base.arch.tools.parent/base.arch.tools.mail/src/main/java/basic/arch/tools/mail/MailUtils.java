package basic.arch.tools.mail;

import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailUtils {
	
	static final Logger logger =LoggerFactory.getLogger(MailUtils.class);

	private static final MailUtils instance = new MailUtils();
	
    private BlockingQueue<MailMessage> mailMessages = new LinkedBlockingQueue<MailMessage>();
    private BlockingQueue<MailMessage> mailInstantMessages = new LinkedBlockingQueue<MailMessage>();
    
    private MailUtils(){
    	new SendEmailThread(this,mailMessages).start();
    	new SendEmailThread(this,mailInstantMessages).start();
    }
    /**
     * 返回唯一实例
     * @return
     */
    public static final MailUtils getInstance(){
    	return instance;
    }
    /**
     * 发送邮件
     * @param to 发送地址
     * @param subject 邮件标题
     * @param body 邮件内容
     */
    public static boolean sendHtmlMail(String to, String subject, String body){
    	return sendHtmlMail(to, null, null, subject, body, false);
    }
    /**
     * 发送文本邮件
     * @param to 发送地址
     * @param subject 邮件标题
     * @param body 邮件内容
     */
    public static boolean sendTextMail(String to, String subject, String body){
    	return sendTextMail(to, null, null, subject, body, false);
    }
    /**
     * 发送邮件
     * @param to 发送地址
     * @param subject 邮件标题
     * @param body 邮件内容
     * @param instant 重要邮件，立即发送
     */
    public static boolean sendHtmlMail(String to, String subject, String body, boolean instant){
    	return sendHtmlMail(to, null, null, subject, body, instant);
    }
    /**
     * 发送文本邮件
     * @param to 发送地址
     * @param subject 邮件标题
     * @param body 邮件内容
     * @param instant 重要邮件，立即发送
     */
    public static boolean sendTextMail(String to, String subject, String body, boolean instant){
    	return sendTextMail(to, null, null, subject, body, instant);
    }
    /**
     * 发送邮件
     * @param to 发送地址
     * @param cc 抄送地址
     * @param bcc 暗送地址
     * @param subject 邮件标题
     * @param body 邮件内容
     */
    public static boolean sendHtmlMail(String to, String cc, String bcc, String subject, String body){
    	return sendHtmlMail(to, cc, bcc, subject, body, false);
    }
    /**
     * 发送文本邮件
     * @param to 发送地址
     * @param cc 抄送地址
     * @param bcc 暗送地址
     * @param subject 邮件标题
     * @param body 邮件内容
     */
    public static boolean sendTextMail(String to, String cc, String bcc, String subject, String body){
    	return sendTextMail(to, cc, bcc, subject, body, false);
    }
    /**
     * 发送邮件
     * @param to 发送地址
     * @param cc 抄送地址
     * @param bcc 暗送地址
     * @param subject 邮件标题
     * @param body 邮件内容
     * @param instant 重要邮件，立即发送
     */
    public static boolean sendTextMail(String to, String cc, String bcc, String subject, String body, boolean instant){
    	if(to == null || to.equals("") || subject == null || subject.equals("") || body == null || body.equals("")){
    		if(logger.isDebugEnabled()){
    			logger.debug("添加邮件失败，接收地址、主题、内容不能为空!");
    		}
    		return false;
    	}
    	return sendMail(new TextMailMessage(to, cc, bcc, subject,body), instant);
    }
    /**
     * 发送邮件
     * @param to 发送地址
     * @param cc 抄送地址
     * @param bcc 暗送地址
     * @param subject 邮件标题
     * @param body 邮件内容
     * @param instant 重要邮件，立即发送
     */
    public static boolean sendHtmlMail(String to, String cc, String bcc, String subject, String body, boolean instant){
    	if(to == null || to.equals("") || subject == null || subject.equals("") || body == null || body.equals("")){
    		if(logger.isDebugEnabled()){
    			logger.debug("添加邮件失败，接收地址、主题、内容不能为空!");
    		}
    		return false;
    	}
    	return sendMail(new HtmlMailMessage(to, cc, bcc, subject,body), instant);
    }
    /**
     * 发送邮件
     * @param message 邮件内容对象
     */
    public static boolean sendMail(MailMessage message, boolean instant){
    	try {
	    	if(instant){
				instance.mailInstantMessages.put(message);
	    	}else{
	    		instance.mailMessages.put(message);
	    	}
    	} catch (InterruptedException e) {
			e.printStackTrace();
			if(logger.isDebugEnabled()){
		   		logger.debug("添加邮件失败，发送线程已经停止！");
		   	}
			return false;
		}
    	if(logger.isDebugEnabled()){
    		logger.debug("添加邮件成功");
    	}
    	return true;
    }
    /**
     * 发送邮件
     * @param messages 邮件内容对象列表
     */
    public int sendMail(Collection<MailMessage> messages){
    	int count = 0;
    	for(MailMessage msg : messages){
    		if(sendMail(msg,false)){
    			count++;
    		}
    	}
    	if(logger.isDebugEnabled()){
    		logger.debug("成功添加"+count+"条邮件");
    	}
    	return count;
    }
    /**
     * 创建文本邮件内容
     * @return
     */
    public static TextMailMessage newTextMail(){
    	return new TextMailMessage();
    }
    /**
     * 创建网页格式邮件内容
     * @return
     */
    public static HtmlMailMessage newHtmlMail(){
    	return new HtmlMailMessage();
    }
    /**
     * 得到所有的未发送邮件内容，可以供给页面查看
     * @return 未发送邮件内容
     */
    public Collection<MailMessage> getAllMessages(){
    	return mailMessages;
    }
    /**
     * 得到所有需要立即发送的未发送邮件内容，可以供给页面查看
     * @return 未发送邮件内容
     */
    public Collection<MailMessage> getAllInstantMessages(){
    	return mailInstantMessages;
    }
    /**
     * 判断是否还有未发送邮件
     * @return 是否还有未发送邮件
     */
    public boolean hasMessages(){
    	return !mailMessages.isEmpty() && !mailInstantMessages.isEmpty();
    }
    
}
