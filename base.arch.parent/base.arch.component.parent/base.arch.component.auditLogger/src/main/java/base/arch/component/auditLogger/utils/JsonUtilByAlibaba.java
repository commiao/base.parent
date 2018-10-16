package base.arch.component.auditLogger.utils;

import java.util.List;


import com.alibaba.fastjson.JSON;

public class JsonUtilByAlibaba {

	private static JsonUtilByAlibaba uniqueInstance = new JsonUtilByAlibaba();;

	private JsonUtilByAlibaba() {
	}

	public static JsonUtilByAlibaba getInstance() {

		return uniqueInstance;

	}

	/**
	 * java对象转换为json字符串
	 * 
	 * @param obj
	 * @return
	 */
	public String javaObjectToString(Object obj) {
		return JSON.toJSONString(obj, true);
	}

	/**
	 * java对象集合转换为json字符串
	 * 
	 * @param obj
	 * @return
	 */
	public String listToString(Object obj) {
		return JSON.toJSONString(obj, true);
	}

	/**
	 * json字符串转换为java对象
	 * 
	 * @param str
	 * @param clazz
	 * @return
	 */
	public <T> Object jsonStringToJavaObject(String str, Class<T> clazz) {

		if (str.indexOf("[") != -1) {
			str = str.replace("[", "");
		}
		if (str.indexOf("]") != -1) {
			str = str.replace("]", "");
		}

		return ((T) JSON.parseObject(str, clazz));

	}

	/**
	 * 封装将json对象转换为java集合对象
	 * 
	 * @param <T>
	 * @param clazz
	 * @param jsons
	 * @return
	 */
	public <T> List<T> getJavaCollection(Class<T> clazz, String jsons) {
		List<T> objs = null;
		
		objs = ((List<T>) JSON.parseArray(jsons, clazz));
		
		return objs;
	}

}
