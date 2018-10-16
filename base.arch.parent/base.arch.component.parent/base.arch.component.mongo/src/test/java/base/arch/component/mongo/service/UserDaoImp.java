package base.arch.component.mongo.service;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import base.arch.component.mongo.AbstractBaseMongoTemplete;
import base.arch.component.mongo.entity.UserInfo;

@Component("UserDaoImp")  
public class UserDaoImp extends AbstractBaseMongoTemplete<UserInfo> implements UserDao {  
  
    /** 
     * 查询所有数据 
     *  
     * @author：tuzongxun 
     * @Title: findAll 
     * @Description: TODO 
     * @param @return 
     * @date May 13, 2016 3:10:29 PM 
     * @throws 
     */  
    @Override  
    public List<UserInfo> findAll() {  
        // 需要设置集合对应的尸体类和相应的集合名，从而查询结果直接映射  
        List<UserInfo> userList = mongoTemplate.findAll(UserInfo.class,  
                "user");  
        return userList;  
    }  
  
    /** 
     * 新增数据 
     *  
     * @author：tuzongxun 
     * @Title: insertUser 
     * @Description: TODO 
     * @param @param user 
     * @date May 13, 2016 3:10:45 PM 
     * @throws 
     */  
    @Override  
    public void insertUser(UserInfo user) {  
        // 设置需要插入到数据库的文档对象  
        save(user);
    }  
  
    /** 
     * 按条件删除数据 
     *  
     * @author：tuzongxun 
     * @Title: removeUser 
     * @Description: TODO 
     * @param @param userName 
     * @date May 13, 2016 3:11:01 PM 
     * @throws 
     */  
    @Override  
    public void removeUser(String userName) {  
        // 设置删除条件，如果条件内容为空则删除所有  
        Query query = new Query();  
        Criteria criteria = new Criteria("userName");  
        criteria.is(userName);  
        query.addCriteria(criteria);  
        mongoTemplate.remove(query);  
        
    }  
  
    /** 
     * 修改数据 
     *  
     * @author：tuzongxun 
     * @Title: updateUser 
     * @Description: TODO 
     * @param @param user 
     * @date May 13, 2016 3:11:12 PM 
     * @throws 
     */  
    @Override  
    public void updateUser(UserInfo user) {  
        // 设置修改条件  
        Query query = new Query();  
        Criteria criteria = new Criteria("userName");  
        criteria.is(user.getUserName());  
        query.addCriteria(criteria);  
        // 设置修改内容  
        Update update = Update.update("password", user.getPassword());  
        // 参数：查询条件，更改结果，集合名  
        mongoTemplate.updateFirst(query, update, "user");  
    }  
  
    /** 
     * 根据条件查询 
     *  
     * @author：tuzongxun 
     * @Title: findForRequery 
     * @Description: TODO 
     * @param @param userName 
     * @date May 13, 2016 4:08:15 PM 
     * @throws 
     */  
    @Override  
    public List<UserInfo> findForRequery(String userName) {  
//        Map<String,String> params = new HashMap<String,String>();
//        params.put("userName", userName);
    	UserInfo user = new UserInfo();
    	user.setUserName(userName);
        return find(getQuery(user));  
    }  
  
}  
