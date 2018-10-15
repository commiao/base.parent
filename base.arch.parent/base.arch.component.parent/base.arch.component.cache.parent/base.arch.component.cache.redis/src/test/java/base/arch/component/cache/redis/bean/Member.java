package base.arch.component.cache.redis.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * 会员
 * @author jannal
 */
public class Member implements Serializable{

	private static final long serialVersionUID = 8490578635775768173L;

	/**
     * 用户id
     */
    private Long id;
    
    /**
     * 用户名
     */
    private String username;
    /**
     * 年龄
     */
    private int age;
    
    /**
     * 食物
     */
    private List<Food> foods = new ArrayList<Food>();
    

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Member [id=" + id + ", username=" + username + ", age=" + age + ", foods=" + foods + "]";
    }
    
}
