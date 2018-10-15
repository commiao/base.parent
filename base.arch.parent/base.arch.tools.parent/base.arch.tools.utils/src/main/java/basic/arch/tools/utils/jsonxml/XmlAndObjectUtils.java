package basic.arch.tools.utils.jsonxml;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * xml与Object转换
 * 
 * @author jannal 2015-12-07
 */
@SuppressWarnings("all")
public class XmlAndObjectUtils {

    /**
     * 对象转xml,默认忽略空元素
     * 
     * @param obj
     *            传入的对象
     * @param map
     *            存放对象与相应的别名
     * @return obj的xml数据格式
     */
    public static <T> String ObjectToXMLByXStream(Object obj, Map<Class<T>, String> map) {
        XStream xstream = new XStream();
        xstream.ignoreUnknownElements();
        xstream.registerConverter(new DateConverter("yyyy-MM-dd hh:mm:ss", new String[] { "yyyy-MM-dd hh:mm:ss", "yyyy-MM-dd" }));
        Set<Entry<Class<T>, String>> entrySet = map.entrySet();
        Iterator<Entry<Class<T>, String>> iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            Entry<Class<T>, String> entry = iterator.next();
            xstream.alias(entry.getValue(), entry.getKey());
        }
        String xml = xstream.toXML(obj);
        return xml;
    }

    /**
     * xml转对象
     * 
     * @param xml
     *            xml字符串
     * @param map
     *            存放对象与相应的别名
     * @return 返回 对象
     */

    public static <T> T XMLToObjectByXStream(String xml, Map<Class<T>, String> map) {
        XStream xstream = new XStream(new DomDriver());
        xstream.ignoreUnknownElements();
        xstream.registerConverter(new DateConverter("yyyy-MM-dd hh:mm:ss", new String[] { "yyyy-MM-dd hh:mm:ss", "yyyy-MM-dd" }));
        Set<Entry<Class<T>, String>> entrySet = map.entrySet();
        Iterator<Entry<Class<T>, String>> iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            Entry<Class<T>, String> entry = iterator.next();
            xstream.alias(entry.getValue(), entry.getKey());
        }
        T t = (T) xstream.fromXML(xml);
        return t;
    }

    public static void main(String[] args) {

    }

}
