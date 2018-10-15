package basic.arch.tools.config;

import javax.annotation.Resource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

/**
 * 读取所有配置信息的bean，不允许自己使用其他方式读取
 * @author jannal
 */
@Component
public class ConfigBean implements ApplicationContextAware,InitializingBean,EmbeddedValueResolverAware {
	
	private static ApplicationContext applicationContext;
	
	/**
	 * mailConfig:邮箱配置文件. 
	 */
	@Resource
	private MailConfig mailConfig;
	
	@Resource
	private PofSmsConfig PofSmsConfig;
	
	
	/**
	 * 获取 mailConfig:邮箱配置文件.
	 * @return the mailConfig
	 */
	public MailConfig getMailConfig() {
		return mailConfig;
	}

	/**
	 * 设置 mailConfig:邮箱配置文件. 
	 * @param mailConfig the mailConfig to set
	 */
	public void setMailConfig(MailConfig mailConfig) {
		this.mailConfig = mailConfig;
	}

	public PofSmsConfig getPofSmsConfig() {
		return PofSmsConfig;
	}

	public void setPofSmsConfig(PofSmsConfig pofSmsConfig) {
		PofSmsConfig = pofSmsConfig;
	}
	
	

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ConfigBean.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static Object getBean(String beanName) {
		return applicationContext.getBean(beanName);
	}

	public static <T> T getBean(String beanName, Class<T> clazz) {
		return applicationContext.getBean(beanName, clazz);
	}
	
	/**
	 * 如果在非spring管理的Bean中获取ConfigBean 就使用这个方法
	 * @return
	 */
	public static ConfigBean getConfigBean() {
		return getBean("configBean", ConfigBean.class);
	}

	/** 
	 * TODO 如果需要手动读取其他配置文件可以在此处进行自定义读取（可选）. 
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet() 
	 */  
	@Override
	public void afterPropertiesSet() throws Exception {
		
	}

	private StringValueResolver stringValueResolver;
	@Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        stringValueResolver = resolver;
    }
 
    public String getPropertiesValue(String name){
        return stringValueResolver.resolveStringValue(name);
    }

}
