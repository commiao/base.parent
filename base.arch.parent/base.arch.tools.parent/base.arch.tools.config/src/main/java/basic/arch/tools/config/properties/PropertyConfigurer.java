package basic.arch.tools.config.properties;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class PropertyConfigurer extends PropertyPlaceholderConfigurer {
    
	private List<ConfigRead> configList;
    
    /**
     * 是否加载插件
     */
    private boolean loadPlugin;
    
    private Logger logger = LoggerFactory.getLogger(PropertyConfigurer.class.getName());

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        //可以在之前做一些加密的工作
        super.processProperties(beanFactoryToProcess, props);
    }

    @Override
    protected void loadProperties(Properties props) throws IOException {
        super.loadProperties(props);
        // 从插件中加载
        if (configList != null && configList.size() > 0) {
            int size = configList.size();
            ConfigRead configRead =null;
            for (int i = 0; i < size; i++) {
                configRead= configList.get(i);
                if(configRead!=null){
                    configRead.loadConfig(props);
                }
            }
        }
        Set<Entry<Object, Object>> entrySet = props.entrySet();
        Iterator<Entry<Object, Object>> iterator = entrySet.iterator();
        logger.info("====================开始输出配置====================");
        while(iterator.hasNext()){
            Entry<Object, Object> entry = iterator.next();
            logger.info("{}:{}",entry.getKey(),entry.getValue());
        }
        logger.info("====================结束输出配置====================");
        
    }

    public List<ConfigRead> getConfigList() {
        return configList;
    }

    public void setConfigList(List<ConfigRead> configList) {
        this.configList = configList;
    }

    public boolean isLoadPlugin() {
        return loadPlugin;
    }

    public void setLoadPlugin(boolean loadPlugin) {
        this.loadPlugin = loadPlugin;
    }
    
}
