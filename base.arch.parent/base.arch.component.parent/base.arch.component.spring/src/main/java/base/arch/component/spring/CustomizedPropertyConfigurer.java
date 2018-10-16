package base.arch.component.spring;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.io.Resource;




/**
 * @author chenxin
 * 
 *
 *
 *
 */
public class CustomizedPropertyConfigurer extends PropertyPlaceholderConfigurer {
	private static Map<String, String> ctxPropertiesMap;
	private static CustomizedPropertyConfigurer configurer;
	private Resource[] locations;
	private String propertyLocationSystemProperty;
	private String defaultPropertyFileName;
	private List<IPropertyPlaceholderConfigurerPlugin> plugins;

	@Override
	public void setLocations(Resource[] locations) {
		this.locations = locations;
	}

	public String getPropertyLocationSystemProperty() {
		return propertyLocationSystemProperty;
	}

	public void setPropertyLocationSystemProperty(
			String propertyLocationSystemProperty) {
		this.propertyLocationSystemProperty = propertyLocationSystemProperty;
	}

	public String getDefaultPropertyFileName() {
		return defaultPropertyFileName;
	}

	public void setDefaultPropertyFileName(String defaultPropertyFileName) {
		this.defaultPropertyFileName = defaultPropertyFileName;
	}

	public CustomizedPropertyConfigurer() {
		super();
		configurer = this;
	}

	@Override
	protected void loadProperties(Properties props) throws IOException {
		locations = this.checkLoactions(locations);
		super.setLocations(locations);
		super.loadProperties(props);
		this.loadPluginConfig(props);
	}
	
	private void loadPluginConfig(Properties props){
		 if(this.plugins != null && this.plugins.size()>0 ){
			 for (int i = 0; i < plugins.size(); i++) {
				  this.getPlugins().get(i).loadConfig(props);
			}
		 }
	}
	
	@Override
	protected void processProperties(
			ConfigurableListableBeanFactory beanFactoryToProcess,
			Properties props) throws BeansException {
		super.processProperties(beanFactoryToProcess, props);
		ctxPropertiesMap = new HashMap<String, String>();
		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			String value = props.getProperty(keyStr);
			ctxPropertiesMap.put(keyStr, value);
		}
	}

	public static CustomizedPropertyConfigurer getInstance() {
		return configurer;
	}

	public String getContextProperty(String name) {
		return ctxPropertiesMap.get(name);
	}

	public Resource[] checkLoactions(Resource[] locations) {
		if (locations != null) {
			List<Resource> resourceList = new java.util.ArrayList<Resource>(
					java.util.Arrays.asList(locations));
			for (int i = 0; i < resourceList.size(); i++) {
				Resource r = (Resource) resourceList.get(i);
				if (!StringUtils.endsWith(r.getFilename(), ".properties")) {
					resourceList.remove(i);
				}
			}
			if (resourceList.size() == 0) {
				return null;
			} else {
				Resource[] ls = (Resource[]) resourceList
						.toArray(new Resource[resourceList.size()]);
				return ls;
			}
		}
		return locations;
	}

	public List<IPropertyPlaceholderConfigurerPlugin> getPlugins() {
		return plugins;
	}

	public void setPlugins(List<IPropertyPlaceholderConfigurerPlugin> plugins) {
		this.plugins = plugins;
	}
}
