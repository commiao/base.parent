package basic.arch.tools.utils.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * CreateDate:2016年9月28日下午12:28:34
 * 
 * @Description: properties文件工具类
 * @author:yuzhao
 * @version V1.0
 */
public class PropertiesUtils {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(PropertiesUtils.class);

	/**
	 * 使用map存放properties对象，key是properties文件名，value是properties对象
	 */
	private static ConcurrentHashMap<String, Properties> propMap = new ConcurrentHashMap<String, Properties>();

	/**
	 * 
	 * @Description::取值. <br/> 
	 * @author yuzhao
	 * @param propFileName
	 * @param key
	 * @param separator
	 * @return
	 */
	public static String[] getValueArr(String propFileName, String key, String separator) {
        String value = getValue(propFileName, key);
        if(StringUtils.isBlank(value)) {
        	return null;
        }
        String[] str = value.split(separator);
        return str;
    }
	
	/**
	 * 
	 * @Description::取值. <br/>
	 * @author yuzhao
	 * @param propFileName
	 * @param key
	 * @return
	 */
	public static String getValue(String propFileName, String key) {
		loadProperties(propFileName);
		Properties p = propMap.get(propFileName);
		if (p == null) {
			return null;
		}
		return p.getProperty(key, null);
	}

	/**
	 * 
	 * @Description::取值——转换成int返回. <br/>
	 * @author yuzhao
	 * @param propFileName
	 * @param key
	 * @return
	 */
	public static int getIntValue(String propFileName, String key) {
		String value = getValue(propFileName, key);
		int intValue = 0;
		try {
			intValue = Integer.parseInt(value);
		} catch (NumberFormatException e) {
			LOGGER.error("properties属性[key=" + key + ", value=" + value + "]，进行int转换时报错。" + e.getMessage(), e);
			intValue = 0;
		}
		return intValue;
	}

	/**
	 * 
	 * @Description::取值——转换成long返回. <br/>
	 * @author yuzhao
	 * @param propFileName
	 * @param key
	 * @return
	 */
	public static long getLongValue(String propFileName, String key) {
		String value = getValue(propFileName, key);
		long longValue = 0;
		try {
			longValue = Long.parseLong(value);
		} catch (NumberFormatException e) {
			LOGGER.error("properties属性[key=" + key + ", value=" + value + "]，进行long转换时报错。" + e.getMessage(), e);
			longValue = 0;
		}
		return longValue;
	}

	/**
	 * 
	 * @Description::通过properties属性名称得到Properties对象. <br/>
	 * @author yuzhao
	 * @param propFileName
	 * @return
	 */
	private static Properties doLoadProperties(String propFileName) {
		Properties properties = new Properties();
		try {
			File f = new File(PropertiesUtils.class.getClassLoader().getResource("").getPath() + propFileName);
			if(f.exists() && f.isFile()) {
				properties.load(new FileInputStream(f));
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException("文件" + propFileName + "没有找到", e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return properties;
	}

	/**
	 * 
	 * @Description::将properties对象放到map中. <br/>
	 * @author yuzhao
	 * @param propFileName
	 * @param p
	 */
	private static void putPropertiesInMap(String propFileName, Properties p) {
		if(p != null && p.size() > 0) {
			propMap.put(propFileName, p);
		}
	}

	/**
	 * 
	 * @Description::判断是否存在对应名称的properties对象. <br/>
	 * @author yuzhao
	 * @param propFileName
	 * @return
	 */
	private static boolean isPropExist(String propFileName) {
		return propMap.get(propFileName) != null;
	}

	/**
	 * 
	 * @Description::加载properties文件. <br/>
	 * @author yuzhao
	 * @param propFileName
	 */
	private static void loadProperties(String propFileName) {
		if (!isPropExist(propFileName)) {
			Properties prop = doLoadProperties(propFileName);
			putPropertiesInMap(propFileName, prop);
		}
	}

}
