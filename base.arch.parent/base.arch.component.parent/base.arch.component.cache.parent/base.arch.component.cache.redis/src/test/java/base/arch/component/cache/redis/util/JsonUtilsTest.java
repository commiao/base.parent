package base.arch.component.cache.redis.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @CreateDate: 2018/12/25 14:21
 * @Description: TODO
 * @author: jingmiao
 * @version: V1.0
 */
public class JsonUtilsTest {

    @Test
    public void testList(){
        List<Person> list = new ArrayList<>();
        Person person1 = new Person("一一",1);
        Person person2 = new Person("二二",2);
        list.add(person1);
        list.add(person2);
        String json = JsonUtils.arr2JsonNet(list);
        System.out.println("json===="+json);
        List<Person> list2 = JsonUtils.json2ArrNet(json,Person.class);
        for(Person p:list2){
            System.out.println(p.getName()+"="+p.getAge());
        }
    }
    @Test
    public void testMap(){
        Map<String,Person> map = new HashMap<>();
        Person person1 = new Person("一一",1);
        Person person2 = new Person("二二",2);
        map.put("1",person1);
        map.put("2",person2);
        String json = JsonUtils.map2JsonFast(map);
        System.out.println("json===="+json);
        Map<String,Person> map2 = JsonUtils.json2MapFast(json, String.class, Person.class);
        for(Map.Entry<String,Person> m:map2.entrySet()){
            System.out.println(m.getKey()+"="+m.getValue().getName()+"||"+m.getValue().getAge());
        }
    }
}
