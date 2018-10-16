package base.arch.component.spring;

import java.util.Properties;

public interface IPropertyPlaceholderConfigurerPlugin {

	public void loadConfig(Properties props);
}
