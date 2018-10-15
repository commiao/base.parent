package base.arch.component.cache.redis;

import base.arch.component.cache.redis.bean.Member;
import base.arch.component.cache.redis.service.RedisForListService;
import base.arch.component.cache.redis.service.RedisService;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @CreateDate: 2018/7/25 10:32
 * @Description: TODO
 * @author: jingmiao
 * @version: V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext-all.xml"})
public class RedisForListServiceTest {
    @Autowired
    private RedisForListService redisForListService;

    @Autowired
    private RedisService redisService;

    @Test
    public void test(){
        List<String> list = new ArrayList<>();
        Member m = new Member();
        m.setAge(18);
        m.setId(1l);
        m.setUsername("十八");
        list.add(JSONObject.toJSONString(m));
        Member m2 = new Member();
        m.setAge(28);
        m.setId(2l);
        m.setUsername("二十八");
        list.add(JSONObject.toJSONString(m2));
        redisForListService.setList("TO:jingmiao",list,60l, TimeUnit.SECONDS);
    }

    @Test
    public void test2(){
        redisService.put("jingmiao:test:000001","8888888888888888888888888");
        redisService.put("jingmiao:test:000001","8888888888888888888888888",1000l,TimeUnit.SECONDS);
    }

    @Test
    public void test3(){

        String str1 = redisService.get("jingmiao:test:000001",false);
        String str2 = redisService.get("jingmiao:test:000001",true);

        System.out.println(str1);
        System.out.println(str2);
    }

}
