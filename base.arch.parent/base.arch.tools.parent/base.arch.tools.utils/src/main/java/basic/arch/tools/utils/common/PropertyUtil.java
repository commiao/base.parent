package basic.arch.tools.utils.common;

import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyUtil {
	private static final Logger logger =LoggerFactory.getLogger(PropertyUtil.class);
	private static Properties properties;
	static {
		// 加载属性文件
		try {
			InputStream inputStream = PropertyUtil.class.getClassLoader()
					.getResourceAsStream("common.properties");
			try {
				properties = new Properties();
				properties.load(inputStream);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				inputStream.close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	public static String getProperty(String key) {
		return properties.getProperty(key);
	}

}
