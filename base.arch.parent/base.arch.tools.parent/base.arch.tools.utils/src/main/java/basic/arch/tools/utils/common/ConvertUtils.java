package basic.arch.tools.utils.common;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * @author jannal 转换工具类
 */
public class ConvertUtils {

	/**
	 * 小写命名规则中，单词与单词之间的间隔
	 */
	private static final char ATTRIBUTE_NAME_SEPARATOR = '_';
	
	public static Map<String,Object> beanToMap(Object... params) {
	    Map<String, Object> param;
        try {
            param = new HashMap<String,Object>();
            for(Object obj:params){
            	Class<?> clazz = obj.getClass();
            	Method[] methods = clazz.getMethods();
            	Pattern pattern = Pattern.compile("^get*");
            	Matcher matcher = null;
            	for(Method m:methods){
            		matcher = pattern.matcher(m.getName());
            			if(matcher.find()){
            				String key = m.getName().replace("get", "");
            				key = (key.charAt(0)+"").toLowerCase().concat(key.substring(1));
            				if("class".equals(key)){
            				    continue;
            				}
            				Object res = m.invoke(obj);
            				if(res == null){
            				    continue;
            				}
            				param.put(key, res);
            			}
            	}
            }
        } catch (SecurityException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
		return param;
	}
	
	/**
	 * 
	 * @Description::bean转换成map，同时进行属性名的转换，转换规则是骆驼命名转换成小写下划线命名. <br/> 
	 * @author yuzhao
	 * @param params
	 * @return
	 */
	public static Map<String,Object> beanToMapWithName(Object... params) {
	    Map<String, Object> param;
        try {
            param = new HashMap<String,Object>();
            for(Object obj:params){
            	Class<?> clazz = obj.getClass();
            	Method[] methods = clazz.getMethods();
            	Pattern pattern = Pattern.compile("^get*");
            	Matcher matcher = null;
            	for(Method m:methods){
            		matcher = pattern.matcher(m.getName());
            			if(matcher.find()){
            				String key = m.getName().replace("get", "");
            				key = (key.charAt(0)+"").toLowerCase().concat(key.substring(1));
            				if("class".equals(key)){
            				    continue;
            				}
            				Object res = m.invoke(obj);
            				if(res == null){
            				    continue;
            				}
            				param.put(camel2LowerWithUnderscores(key), res);
            			}
            	}
            }
        } catch (SecurityException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
		return param;
	}
	
	public static Map<String,Object> beanToMap2(Object... params){
	    try {
            Map<String,Object> param = new HashMap<String,Object>();
            Map<String, Object> describe=null;
            for(Object obj:params){
                describe = PropertyUtils.describe(obj);
                param.putAll(describe);
            }
            return param;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
	}
	
	public static Map<String, String> transBeanToMap(Object obj) {
        if (obj == null) {
            return null;
        }
        Map<String, String> map = new HashMap<String, String>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo
                    .getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    String valueStr = null;
                    if (value != null) {
                        valueStr = value.toString();
                    } else {
                        valueStr = "";
                    }
                    map.put(key, valueStr);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return map;
    }
	
	public static void transMapToBean(Map<String, Object> map, Object obj) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if (map.containsKey(key)) {
                    Object value = map.get(key);
                    // 得到property对应的setter方法
                    Method setter = property.getWriteMethod();
                    setter.invoke(obj, value);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	
	public static Map<?,?> nullMapToEmptyMap(Map<?, ?> sourceMap){
	    if(sourceMap==null||sourceMap.isEmpty()){
	        sourceMap=Collections.EMPTY_MAP;
        }
	    return sourceMap;
	}
	
	/**
	 * 
	 * @Description::骆驼命名转换小写下划线命名. <br/> 
	 * @author yuzhao
	 * @param input
	 * @return
	 */
	public static String camel2LowerWithUnderscores(String input) {
		if (input == null)
			return input; // garbage in, garbage out
		int length = input.length();
		StringBuilder result = new StringBuilder(length * 2);
		int resultLength = 0;
		boolean wasPrevTranslated = false;
		for (int i = 0; i < length; i++) {
			char c = input.charAt(i);
			if (i > 0 || c != ATTRIBUTE_NAME_SEPARATOR) // skip first starting underscore
			{
				if (Character.isUpperCase(c)) {
					if (!wasPrevTranslated && resultLength > 0 && result.charAt(resultLength - 1) != ATTRIBUTE_NAME_SEPARATOR) {
						result.append(ATTRIBUTE_NAME_SEPARATOR);
						resultLength++;
					}
					c = Character.toLowerCase(c);
					wasPrevTranslated = true;
				} else {
					wasPrevTranslated = false;
				}
				result.append(c);
				resultLength++;
			}
		}
		return resultLength > 0 ? result.toString() : input;
	}
	
	public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }
 
        s = s.toLowerCase();
 
        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
 
            if (c == ATTRIBUTE_NAME_SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }
 
        return sb.toString();
    }
	
	
}
