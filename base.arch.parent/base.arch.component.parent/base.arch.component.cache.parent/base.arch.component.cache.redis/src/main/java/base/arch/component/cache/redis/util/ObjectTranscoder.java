package base.arch.component.cache.redis.util;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @CreateDate: 2018/9/25 18:06
 * @Description: TODO
 * @author: jingmiao
 * @version: V1.0
 */
public class ObjectTranscoder {
    public static byte[] serialize(Object value) {
        if (value == null) {
            throw new NullPointerException("Can't serialize null");
        }
        byte[] rv = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;
        try {
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            os.writeObject(value);
            os.close();
            bos.close();
            rv = bos.toByteArray();
        } catch (IOException e) {
            throw new IllegalArgumentException("Non-serializable object", e);
        } finally {
            try {
                if (os != null) os.close();
                if (bos != null) bos.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return rv;
    }

    public static Object deserialize(byte[] in) {
        Object rv = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream is = null;
        try {
            if (in != null) {
                bis = new ByteArrayInputStream(in);
                is = new ObjectInputStream(bis);
                rv = is.readObject();
                is.close();
                bis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
                if (bis != null) bis.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return rv;
    }

   /**
    * @author ligh4 2015年3月23日下午1:32:04
    * @param obj
    * @return null if failed.
    * @throws Exception
    */
   public static synchronized String ToString(Object obj) {
       Kryo kryo = new Kryo();
       ByteOutputStream stream = new ByteOutputStream();
       Output output = new Output(stream);
       kryo.writeClassAndObject(output, obj);
       output.flush();
       String str = null;
       try {
           byte[] bytes = stream.getBytes();
           str = new String(bytes, "ISO8859-1");
       } catch (Exception e) {
           str = null;
       } finally {
           output.close();
           stream.close();
       }
       return str;
   }
   
   /**
    * @author ligh4 2015年3月23日下午1:32:19
    * @param str
    * @return null if failed.
    * @throws Exception
    */
   public static synchronized Object FromString(String str) {
       Kryo kryo = new Kryo();
       try {
           byte[] bytes = str.getBytes("ISO8859-1");
           Input input = new Input(bytes);
           Object obj = kryo.readClassAndObject(input);
           input.close();
           return obj;
       } catch (Exception e) {
           return null;
       }
   }
   static class Persion {
       public String name;
       public int    age;
       public Persion(String name, int age) {
           this.name = name;
           this.age = age;
       }
       public Persion() {
           
        }
   }
   
   /**
    * @author ligh4 2015年3月20日下午4:07:50
    * @param args
    */
   @SuppressWarnings({ "unchecked", "rawtypes", "unused" })
   public static void main(String[] args) throws Exception {
       Map<String, Persion> map = new HashMap<>();
       map.put("cn1", new Persion("中国1", 30));
       map.put("cn2", new Persion("中国2", 30));
       Map<Integer, Map<String, Persion>> classes = new HashMap<>();
       classes.put(1, map);
       String str = ObjectTranscoder.ToString(classes);
       System.out.println("============="+str.length() + ":" + str);
       Map<String, Persion> map2 = (Map) ((Map) ObjectTranscoder.FromString(str)).get(1);
       Persion p1 = map2.get("cn2");
       System.out.println(JsonUtils.bean2Json(p1));
   }
}
