package base.arch.component.spring.hession.spring.client;

import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

/**
 * CreateDate:2016年12月7日上午10:03:30 
 * @Description:  用于读取spring管理的properties
 * @author:jannal
 * @version V1.0
 */
@Component("springManagerPropertiesUtils")
public class SpringManagerPropertiesUtils implements EmbeddedValueResolverAware {
 
    private  StringValueResolver stringValueResolver;
 
    @Override
    public  void setEmbeddedValueResolver(StringValueResolver resolver) {
        stringValueResolver = resolver;
    }
 
    public  String getPropertiesValue(String name){
        name = "${" + name + "}";
        return stringValueResolver.resolveStringValue(name);
    }
}
