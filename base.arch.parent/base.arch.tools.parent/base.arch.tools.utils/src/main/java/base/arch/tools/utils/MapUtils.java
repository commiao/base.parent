package base.arch.tools.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @CreateDate: 2018/10/24 17:37
 * @Description: TODO
 * @author: jingmiao
 * @version: V1.0
 */
public class MapUtils {


    /**
     * 使用reflect进行转换
     */

        public static <T> T mapToObject(Map<String, Object> map, Class<T> beanClass) throws Exception {
            if (map == null)
                return null;

            T obj = beanClass.newInstance();

            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }

                field.setAccessible(true);
                field.set(obj, map.get(field.getName()));
            }

            return obj;
        }

        public static Map<String, Object> objectToMap(Object obj) throws Exception {
            if (obj == null) {
                return null;
            }

            Map<String, Object> map = new HashMap<String, Object>();

            Field[] declaredFields = obj.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }

            return map;
        }


       static class Test{
            Date date;
            BigDecimal bigDecimal;
            String str;
            double dou;
            int i;

            public Date getDate() {
                return date;
            }

            public void setDate(Date date) {
                this.date = date;
            }

            public BigDecimal getBigDecimal() {
                return bigDecimal;
            }

            public void setBigDecimal(BigDecimal bigDecimal) {
                this.bigDecimal = bigDecimal;
            }

            public String getStr() {
                return str;
            }

            public void setStr(String str) {
                this.str = str;
            }

            public double getDou() {
                return dou;
            }

            public void setDou(double dou) {
                this.dou = dou;
            }

            public int getI() {
                return i;
            }

            public void setI(int i) {
                this.i = i;
            }
        }

    public static void main(String[] args) throws Exception {
        Test t = new Test();
        t.setBigDecimal(new BigDecimal(6.321f));
        t.setDate(new Date());
        t.setDou(3.21d);
        t.setI(99);
        t.setStr("test");

        Map<String,Object> m = MapUtils.objectToMap(t);

        Test tt = MapUtils.mapToObject(m,Test.class);

    }

}
