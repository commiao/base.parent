package base.arch.tools.utils.jsonxml;

import base.arch.tools.utils.entity.Test;

import java.util.Date;

/**
 * @CreateDate: 2019/1/24 11:33
 * @Description: TODO
 * @author: jingmiao
 * @version: V1.0
 */
public class JsonOrgUtilsTest {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        Test t = new Test();
        t.setDate(new Date());
        t.setError(true);
        t.setMsg("jingmiao");
        t.setId(1);
        t.setIdl(1l);
        System.out.println("开始转json======start==========");
        long start = System.currentTimeMillis();
        String json = JsonOrgUtils.toJson(t);
        long end = System.currentTimeMillis();
        System.out.println("开始转json=======end=========耗时：" + (end - start) + "ms.");
        System.out.println(json);
        System.out.println("开始转bean======start==========");
        long start2 = System.currentTimeMillis();
        Test tt = JsonOrgUtils.fromJson(json,Test.class);
        long end2 = System.currentTimeMillis();
        System.out.println("开始转bean=======end=========耗时：" + (end2 - start2) + "ms.");
        System.out.println(tt.getDate());
    }
}
