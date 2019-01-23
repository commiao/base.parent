package base.arch.component.cache.redis.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtils {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);
    /**
     * @description <p>内外层一起解析 fastjson</p>
     * @param
     * @return
     * @author heshiyuan
     * @date 2018/8/16 14:25
     */
    public static <T> T json2ObjStrong(String json, Class<T> clazz) {
        logger.debug("正在使用的是新版增强json解析工具-start");
        T t = JSON.parseObject(json, clazz);
        logger.debug("正在使用的是新版增强json解析工具-end");
        return t;
    }

    /**
     * json转成成Object对象 net.sf
     * @param json
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T json2Obj(String json, Class<T> clazz) {
        JSONObject jsonObject = JSONObject.fromObject(json);
        return (T) JSONObject.toBean(jsonObject, clazz);
    }

    /**
     * 
     * @param json （json中Date类型解析有问题）net.sf
     * @param clazz object类名
     * @param classMap object类的泛型
     * @return
     */
    @SuppressWarnings("unchecked")
	public static <T> T json2Obj(String json, Class<T> clazz, Map<String, Class<?>> classMap) {
        JSONObject jsonObject = JSONObject.fromObject(json);
        return (T) JSONObject.toBean(jsonObject, clazz, classMap);
    }


    public static <T>String bean2Json(T bean){
        return bean2Json(bean,null);
    }

    /**
     * 将对象转换为json字符串 fastjson
     * @param bean
     * @param dateFormat "yyyy-MM-dd HH:mm:ss.SSS" 是否格式化日期 Date
     * @param <T>
     * @return
     */
    public static <T>String bean2Json(T bean, String dateFormat){
        if(StringUtils.isBlank(dateFormat)) return com.alibaba.fastjson.JSONObject.toJSONString(bean);
        return JSON.toJSONStringWithDateFormat(bean,dateFormat);
    }

    /**
     * @Description::json转换为列表 fastjson. <br/>
     * @author hehch
     * @param json
     * @param clazz
     * @return
     */
    public static <T> List<T> json2ArrNet(String json, Class<T> clazz) {
        JSONArray array = JSONArray.fromObject(json);
        T bean = null;
        try {
            bean = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return JSONArray.toList(array,bean,new JsonConfig());
    }
    public static <T> String arr2JsonNet(T bean){
        return JSONArray.fromObject(bean).toString();
    }
    /**
     * @Description::json转换为列表 fastjson. <br/>
     * @author hehch
     * @param json
     * @param clazz
     * @return
     */
    public static <T> List<T> json2ArrFast(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }
    public static <T> String arr2JsonFast(T bean){
        return JSON.toJSONString(bean);
    }

    public static <T>List<T> json2List(List<String> jsonList,Class<T> clazz){
		List<T> list = new ArrayList<>();
		T info = null;
		for(String json:jsonList){
			info = json2Obj(json,clazz);
			list.add(info);
		}
		return list;
	}

    /**
     * 将list中的对象转换为json字符串  非date类型
     * @param list
     * @param <T>
     * @return
     */
    public static <T>List<String> list2Json(List<T> list){
        return list2Json(list,null);
    }

    /**
     * 将list中的对象转换为json字符串
     * @param list
     * @param dateFormat "yyyy-MM-dd HH:mm:ss.SSS" 是否格式化日期 Date
     * @param <T>
     * @return
     */
    public static <T>List<String> list2Json(List<T> list, String dateFormat){
        List<String> jsonList = new ArrayList<>();
        for(T info:list){
            jsonList.add(bean2Json(info,dateFormat));
        }
        return jsonList;
    }

	public static <T>Map<String,T>json2Map(Map<Object,Object> jsonMap,Class<T> clazz){
        Map<String,T> result = new HashMap<>();
        for(Map.Entry<Object,Object> entity:jsonMap.entrySet()){
            result.put(entity.getKey().toString(),json2ObjStrong(entity.getValue().toString(), clazz));
        }
        return result;
    }

    public static <K,V>Map <K,V> json2MapFast(String jsonMap,Class<K> keyType,Class<V> valueType){
        return JSON.parseObject(jsonMap,new TypeReference<Map<K,V>>(keyType, valueType){});
    }

    public static <T>String map2JsonFast(Map<String,T> map){
        return JSON.toJSONString(map,true);
    }

    /**
     * 将map中的value转换为json字符串
     * @param map
     * @param dateFormat "yyyy-MM-dd HH:mm:ss.SSS" 是否格式化日期 Date
     * @param <T>
     * @return
     */
	public static <T>Map<String,String>map2Json(Map<String,T> map,String dateFormat){
        Map<String,String> result = new HashMap<>();
        for(Map.Entry<String,T> entity:map.entrySet()){
            result.put(entity.getKey(),bean2Json(entity.getValue(),dateFormat));
        }
        return result;
    }

}
