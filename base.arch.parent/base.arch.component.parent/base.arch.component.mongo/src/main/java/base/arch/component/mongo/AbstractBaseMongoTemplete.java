package base.arch.component.mongo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import base.arch.component.mongo.constant.MongoConstant;

public abstract class AbstractBaseMongoTemplete<T> implements ApplicationContextAware {

	protected MongoTemplate mongoTemplate;

	/**
	 * @Description 根据配置文件设置mongoTemplate
	 * @param mongoTemplate
	 */
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		MongoTemplate mongoTemplate = applicationContext.getBean("mongoTemplate", MongoTemplate.class);
		setMongoTemplate(mongoTemplate);
	}
	
	
    public List<T> find(Query query) {  
        return mongoTemplate.find(query, this.getEntityClass());  
    }  
  
    public T findOne(Query query) {  
        return mongoTemplate.findOne(query, this.getEntityClass());  
    }  
  
    public void update(Query query, Update update) {  
        mongoTemplate.findAndModify(query, update, this.getEntityClass());  
    }  
  
    public T save(T entity) {  
        mongoTemplate.insert(entity);  
        return entity;  
    }
    
    public void delete(Query query){
    	mongoTemplate.remove(query, this.getEntityClass());
    }
    
    public T delete(T entity){
    	mongoTemplate.remove(entity);
    	return entity;
    }
  
    public T findById(String id) {  
        return mongoTemplate.findById(id, this.getEntityClass());  
    }  
  
    public T findById(String id, String collectionName) {  
        return mongoTemplate.findById(id, this.getEntityClass(), collectionName);  
    }  
      
    public Page<T> findPage(Page<T> page,Query query){  
        long count = this.count(query);  
        page.setTotal(count);  
        int pageNumber = page.getPageNumber();  
        int pageSize = page.getPageSize();  
        query.skip((pageNumber - 1) * pageSize).limit(pageSize);  
        List<T> rows = this.find(query);  
        page.setRows(rows);  
        return page;  
    }  
      
    public long count(Query query){  
        return mongoTemplate.count(query, this.getEntityClass());  
    }  
      
  
    /** 
     * 获取需要操作的实体类class 
     *  
     * @return 
     */  
    private Class<T> getEntityClass(){  
        return ReflectionUtils.getSuperClassGenricType(getClass());  
    }
    
    public Query getQuery(String key){
    	StringBuffer sb = new StringBuffer();
    	sb.append("^.*").append(key).append(".*$");
    	//完全匹配
    	//Pattern pattern = Pattern.compile("^name$", Pattern.CASE_INSENSITIVE);
    	//右匹配
    	//Pattern pattern = Pattern.compile("^.*name$", Pattern.CASE_INSENSITIVE);
    	//左匹配
    	//Pattern pattern = Pattern.compile("^name.*$", Pattern.CASE_INSENSITIVE);
    	//模糊匹配
    	Pattern pattern = Pattern.compile(sb.toString(), Pattern.CASE_INSENSITIVE);
    	Query query = new Query();
    	Criteria criteria = new Criteria(key);  
		criteria.is(pattern);  
		query.addCriteria(criteria);
		return query;
    }
    
	public static Map<String,Object> beanToMap(Object... params) {
	    Map<String, Object> param;
        try {
            param = new HashMap<String,Object>();
            for(Object obj:params){
            	Class<?> clazz = obj.getClass();
            	Method[] methods = clazz.getMethods();
            	Pattern pattern = Pattern.compile("^get*");
            	Matcher matcher = null;
            	for(Method m:methods){
            		matcher = pattern.matcher(m.getName());
            			if(matcher.find()){
            				String key = m.getName().replace("get", "");
            				key = (key.charAt(0)+"").toLowerCase().concat(key.substring(1));
            				if("class".equals(key)){
            				    continue;
            				}
            				Object res = m.invoke(obj);
            				if(res == null){
            				    continue;
            				}
            				param.put(key, res);
            			}
            	}
            }
        } catch (SecurityException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
		return param;
	}
    
    public Query getQuery(T entity){
    	return getQuery(beanToMap(entity),MongoConstant.QueryTypeConstant.QUERY_TYPE_VAGUE_ALL);
    }
    
    public Query getQuery(Map<String,Object> params, int queryTypeVague){
    	if(params==null || params.isEmpty() || params.size()<1) return null;
    	
    	
    	Query query = new Query(); 
    	for(Entry<String, Object> param:params.entrySet()){
    		if(param.getValue()==null) continue;
    		Criteria criteria = new Criteria(param.getKey());  
    		StringBuffer sb = new StringBuffer();
    		if(queryTypeVague==MongoConstant.QueryTypeConstant.QUERY_TYPE_VAGUE_ALL){
				//模糊匹配
				sb.append("^.*").append(param.getValue().toString()).append(".*$");
    		}else if(queryTypeVague==MongoConstant.QueryTypeConstant.QUERY_TYPE_VAGUE_LEFT){
				//右匹配
				sb.append("^.*").append(param.getValue().toString()).append("$");
    		}else if(queryTypeVague==MongoConstant.QueryTypeConstant.QUERY_TYPE_VAGUE_RIGHT){
				//模糊匹配
				sb.append("^.*").append(param.getValue().toString()).append(".*$");
    		}else{
				//完全匹配
				sb.append("^").append(param.getValue().toString()).append("$");
			}
    		
    		Pattern pattern = Pattern.compile(sb.toString(), Pattern.CASE_INSENSITIVE);
    		criteria.is(pattern);  
    		query.addCriteria(criteria);
    	}
    	return query;
    }
    
}
