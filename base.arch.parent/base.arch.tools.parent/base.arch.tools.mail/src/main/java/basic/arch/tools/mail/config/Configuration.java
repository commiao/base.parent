package basic.arch.tools.mail.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import basic.arch.tools.config.ConfigBean;
import basic.arch.tools.config.MailConfig;

@Component
public class Configuration {
	
	private static ConfigBean configBean;
	
	@Autowired
	private Configuration(ConfigBean configBean) {
		Configuration.configBean = configBean;
	}
	
	public static MailConfig getMailConfig(){
		return configBean.getMailConfig();
	}
	
	/**
	 * 获取 port:邮箱服务器端口号.
	 * @return the port
	 */
	public static String getPort() {
		return getMailConfig().getMailServerPort();
	}
	/**
	 * 获取 server:邮箱服务器地址.
	 * @return the server
	 */
	public static String getServer() {
		return getMailConfig().getMailServerName();
	}
	/**
	 * 获取 from:邮件发送者.
	 * @return the from
	 */
	public static String getFrom() {
		return getMailConfig().getMailServerFrom();
	}
	/**
	 * 获取 user:邮箱服务器账户.
	 * @return the user
	 */
	public static String getUser() {
		return getMailConfig().getMailServerAccount();
	}
	/**
	 * 获取 password:邮箱服务器密码.
	 * @return the password
	 */
	public static String getPassword() {
		return getMailConfig().getMailServerPassword();
	}
	/**
	 * 获取 istls:默认不启用tls加密.
	 * @return the istls
	 */
	public static Boolean getIstls() {
		Boolean istls = null;
		if(StringUtils.isNotBlank(getMailConfig().getMailServerTls())){
			istls = Boolean.valueOf(getMailConfig().getMailServerTls());
		}
		return istls==null?false:istls;
	}

	/**
	 * 获取 两次发送间隔的毫秒数
	 * @return the interval
	 */
	public static long getInterval() {
		Long interval = null;
		if(StringUtils.isNotBlank(getMailConfig().getInterval())){
			interval = Long.valueOf(getMailConfig().getInterval());
		}
		return interval==null?1000l:interval;
	}

	/**
	 * 获取 如果发送出现错误，最大的重试次数
	 * @return the maxRetryCount
	 */
	public static int getMaxRetryCount() {
		Integer maxRetryCount = null;
		if(StringUtils.isNotBlank(getMailConfig().getMaxRetryCount())){
			maxRetryCount = Integer.valueOf(getMailConfig().getMaxRetryCount());
		}
		return maxRetryCount==null?3:maxRetryCount;
	}
	
}
