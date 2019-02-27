package base.arch.component.cache.redis.util;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

/**
 * @CreateDate: 2018/12/25 14:20
 * @Description: TODO
 * @author: jingmiao
 * @version: V1.0
 */
public class Person implements Serializable{

    private String name;

    private int age;

    @JSONField(format="yyyyMMdd")
    private Date date = new Date();

    public Person() { }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public int getAge() {

        return age;
    }

    public void setAge(int age) {

        this.age = age;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
