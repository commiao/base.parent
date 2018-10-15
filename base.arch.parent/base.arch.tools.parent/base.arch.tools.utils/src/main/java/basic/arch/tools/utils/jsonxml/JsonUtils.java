package basic.arch.tools.utils.jsonxml;

import com.alibaba.fastjson.JSON;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonUtils {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);
    /**
     * json转成成Object对象
     * 
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
     * @description <p>内外层一起解析</p>
     * @param
     * @return
     * @author heshiyuan
     * @date 2018/8/16 14:25
     */
    public static <T> T json2ObjStrong(String json, Class<T> clazz) {
        logger.info("正在使用的是新版增强json解析工具");
        return JSON.parseObject(json, clazz);
    }

    /**
     * 
     * @param json （json中Date类型解析有问题）
     * @param clazz object类名
     * @param classMap object类的泛型
     * @return
     */
    @SuppressWarnings("unchecked")
	public static <T> T json2Obj(String json, Class<T> clazz, Map<String, Class<?>> classMap) {
        JSONObject jsonObject = JSONObject.fromObject(json);
        return (T) JSONObject.toBean(jsonObject, clazz, classMap);
    }
    

    /**
     * @Description::json转换为列表. <br/>
     * @author hehch
     * @param json
     * @param clazz
     * @return
     */
    public static <T> List<T> json2Arr(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
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
	
    public static <T>List<String> list2Json(List<T> list){
		List<String> jsonList = new ArrayList<>();
		for(T info:list){
			jsonList.add(bean2Json(info));
		}
		return jsonList;
	}
	
    public static <T>String bean2Json(T bean){
		return com.alibaba.fastjson.JSONObject.toJSONString(bean);
	}
}
