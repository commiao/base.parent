package basic.arch.tools.utils.velocity.toolbox;

import basic.arch.tools.utils.common.PropertiesUtils;

/**
 * 
 * CreateDate:2016年9月28日下午2:44:03 
 * @Description: velocity 获取properties值工具类  
 * @author:yuzhao
 * @version V1.0
 */
public class PropertiesToolBox {

	/**
	 * 
	 * @Description::获取站点url. <br/> 
	 * @author yuzhao
	 * @param file properties文件名
	 * @param key properties key
	 * @return
	 */
	public static String getSiteUrl(String file, String key) {
		return getPropValue(file, key);
	}
	
	/**
	 * 
	 * @Description::获取properties值. <br/> 
	 * @author yuzhao
	 * @param file properties文件名
	 * @param key properties key
	 * @return
	 */
	public static String getPropValue(String file, String key) {
		return PropertiesUtils.getValue(file, key);
	}
	
}
