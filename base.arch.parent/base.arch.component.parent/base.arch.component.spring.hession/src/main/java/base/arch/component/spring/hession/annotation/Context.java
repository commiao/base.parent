package base.arch.component.spring.hession.annotation;

import org.springframework.context.ApplicationContext;

import base.arch.component.spring.hession.spring.client.SpringManagerPropertiesUtils;

public enum Context {
    //默认的URL前缀key，如果需要增加，则在此类中增加即可
    DEFALUT_URL_PRE("hession.remote.defalut.url");
    
    //远程url的key
    private String remoteUrlConfigKey;
    
    private ApplicationContext applicationContext;
 
	private Context(String remoteUrlConfigKey) {
        this.remoteUrlConfigKey = remoteUrlConfigKey;
    }

	public String getRemoteUrl() {
        return getValue(remoteUrlConfigKey);
    }
	
    //根据Key读取Value
    public  String getValue(String key) {
        if(applicationContext==null){
            throw new RuntimeException("applicationContext 没有正确注入");
        }
        SpringManagerPropertiesUtils springManagerPropertiesUtils = applicationContext.getBean(SpringManagerPropertiesUtils.class);
        String value = springManagerPropertiesUtils.getPropertiesValue(key);
        return value;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}