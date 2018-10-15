package base.arch.component.cache.redis;

import base.arch.component.cache.redis.handle.HandleCache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @CreateDate: 2018/8/1 4:00
 * @Description: TODO
 * @author: jingmiao
 * @version: V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext-all.xml"})
public class HandleCacheTest {
    @Test
    public void test(){
        HandleCache.setObjectForOneDay("jingmiao:test","11111111111111111111111");
    }
}
