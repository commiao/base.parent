package basic.arch.tools.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component
public class PofSmsConfig {

	/**
	 * 获取短信发送接口URL. 
	 */
	@Value("${sms.url}")
	private String smsUrl;
	
	/**
	 * 获取短信接口用户名. 
	 */
	@Value("${sms.username}")
	private String smsUserName;

	/**
	 * 获取短信接口密码. 
	 */
	@Value("${sms.password}")
	private String smsPassword;
	
	/**
	 * 短信通道状态 0=close 1=open. 
	 */
	@Value("${sms.status}")
	private String smsStatus;
	
	/**
	 * 获取短信来源名称. 
	 */
	@Value("${sms.sourceName}")
	private String smsSourceName;

	/**
	 * 获取短信验证码位数.
	 */
	@Value("${sms.code.length}")
	private String smsCodeLength;

	/**
	 * 获取 获取短信发送接口URL.
	 * @return the smsUrl
	 */
	public String getSmsUrl() {
		return smsUrl;
	}

	/**
	 * 获取 获取短信接口用户名.
	 * @return the smsUserName
	 */
	public String getSmsUserName() {
		return smsUserName;
	}

	/**
	 * 获取 获取短信接口密码.
	 * @return the smsPassword
	 */
	public String getSmsPassword() {
		return smsPassword;
	}

	/**
	 * 获取 短信通道状态 0=close 1=open.
	 * @return the smsStatus
	 */
	public String getSmsStatus() {
		return smsStatus;
	}

	/**
	 * 获取 获取短信来源名称.
	 * @return the smsSourceName
	 */
	public String getSmsSourceName() {
		return smsSourceName;
	}

	/**
	 * 获取 获取短信验证码位数.
	 * @return the smsCodeLength
	 */
	public String getSmsCodeLength() {
		return smsCodeLength;
	}
	
}
