package base.arch.component.cache.redis;

import base.arch.component.cache.redis.bean.Food;
import base.arch.component.cache.redis.service.RedisHashService;
import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @CreateDate: 2018/7/31 17:59
 * @Description: TODO
 * @author: jingmiao
 * @version: V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext-all.xml"})
public class RedisHashServiceTest {
    @Autowired
    private RedisHashService redisHashService;
    Map<String, String> map = new HashMap<>();
    @Before
    public void before(){
        Food f = new Food();
        f.setName("name1");
        f.setSource("source1");
        f.setWeight(1);
        map.put("1", JSON.toJSONString(f));
        Food f2 = new Food();
        f2.setName("name2");
        f2.setSource("source2");
        f2.setWeight(2);
        map.put("2", JSON.toJSONString(f2));
        Food f3 = new Food();
        f3.setName("name3");
        f3.setSource("source3");
        f3.setWeight(3);
        map.put("3", JSON.toJSONString(f3));
    }

    @Test
    public void testput() {
        for ( Map.Entry<String,String> entry:map.entrySet()) {
            redisHashService.put("jingmiao:test", entry.getKey(), entry.getValue(),true);
        }
    }

    @Test
    public void testputAll(){
        redisHashService.put("jingmiao:testputall",map);
    }

}
