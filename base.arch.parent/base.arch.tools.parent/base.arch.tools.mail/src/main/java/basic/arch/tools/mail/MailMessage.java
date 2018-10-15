package basic.arch.tools.mail;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

/**
 * 内部接口，邮件内容
 */
public interface MailMessage{
	public void addSendCount();
    public boolean isMaxSendCount(int maxRetryCount);
	public MimeMessage getMimeMessage(Session session,String from) throws MessagingException;
}
