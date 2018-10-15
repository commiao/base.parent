package basic.arch.tools.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailConfig {

	/**
	 * mailServerPort:邮箱服务器端口号. 
	 */
	@Value("${mail.server.port}")
	private String mailServerPort;
	
	/**
	 * mailServerName:邮箱服务器地址. 
	 */
	@Value("${mail.server.name}")
	private String mailServerName;
	
	/**
	 * mailServerAccount:邮箱服务器帐号. 
	 */
	@Value("${mail.server.account}")
	private String mailServerAccount;
	
	/**
	 * mailServerPassword:邮箱服务器密码. 
	 */
	@Value("${mail.server.password}")
	private String mailServerPassword;
	
	/**
	 * mailServerFrom:邮箱服务器发送地址. 
	 */
	@Value("${mail.server.from}")
	private String mailServerFrom;
	
	/**
	 * mailServerTls:是否启用tls加密. 
	 */
	@Value("${mail.server.tls}")
	private String mailServerTls;
	
	/**
	 * mailErrorTo:错误邮件接收地址. 
	 */
	@Value("${mail.error.to}")
	private String mailErrorTo;
	
	/**
	 * mailErrorCc:错误邮件抄送地址. 
	 */
	@Value("${mail.error.cc}")
	private String mailErrorCc;
	
	/**
	 * interval:两次发送间隔的毫秒数. 
	 */
	@Value("${mail.server.interval}")
	private String interval;
	
	/**
	 * maxRetryCount:如果发送出现错误，最大的重试次数. 
	 */
	@Value("${mail.max.send.count}")
	private String maxRetryCount;

	

	/**
	 * 获取 mailServerPort:邮箱服务器端口号.
	 * @return the mailServerPort
	 */
	public String getMailServerPort() {
		return mailServerPort;
	}

	/**
	 * 获取 mailServerName:邮箱服务器地址.
	 * @return the mailServerName
	 */
	public String getMailServerName() {
		return mailServerName;
	}

	/**
	 * 获取 mailServerAccount:邮箱服务器帐号.
	 * @return the mailServerAccount
	 */
	public String getMailServerAccount() {
		return mailServerAccount;
	}

	/**
	 * 获取 mailServerPassword:邮箱服务器密码.
	 * @return the mailServerPassword
	 */
	public String getMailServerPassword() {
		return mailServerPassword;
	}

	/**
	 * 获取 mailServerFrom:邮箱服务器发送地址.
	 * @return the mailServerFrom
	 */
	public String getMailServerFrom() {
		return mailServerFrom;
	}

	/**
	 * 获取 mailServerTls:是否启用tls加密.
	 * @return the mailServerTls
	 */
	public String getMailServerTls() {
		return mailServerTls;
	}

	/**
	 * 获取 mailErrorTo:错误邮件接收地址.
	 * @return the mailErrorTo
	 */
	public String getMailErrorTo() {
		return mailErrorTo;
	}

	/**
	 * 获取 mailErrorCc:错误邮件抄送地址.
	 * @return the mailErrorCc
	 */
	public String getMailErrorCc() {
		return mailErrorCc;
	}

	/**
	 * 获取 interval:两次发送间隔的毫秒数.
	 * @return the interval
	 */
	public String getInterval() {
		return interval;
	}

	/**
	 * 获取 maxRetryCount:如果发送出现错误，最大的重试次数.
	 * @return the maxRetryCount
	 */
	public String getMaxRetryCount() {
		return maxRetryCount;
	}
	
}
