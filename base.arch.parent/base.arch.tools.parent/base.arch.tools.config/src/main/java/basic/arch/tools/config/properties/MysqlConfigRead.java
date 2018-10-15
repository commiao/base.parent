package basic.arch.tools.config.properties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MysqlConfigRead implements ConfigRead {

    private Logger logger =LoggerFactory.getLogger(this.getClass());
    
    //环境
    private String env=MysqlJdbcConfig.instance.env();
    //项目名称(必须唯一)
    private String name=MysqlJdbcConfig.instance.name();
    //是否覆盖已有的属性
    private boolean override=true;
    //排除属性(这些属性不在数据库中读取)
    private Set<String> excludeKeys;
    
    @Override
    public void loadConfig(Properties properties) {
        if(StringUtils.isBlank(env)||StringUtils.isBlank(name)){
            throw new IllegalArgumentException("env和name必须初始化...");
        }
        Connection connection =null;
        try {
            QueryRunner queryRunner = new QueryRunner();
            connection = getConnection(MysqlJdbcConfig.instance.driverClass(), //
                    MysqlJdbcConfig.instance.url(),//
                    MysqlJdbcConfig.instance.username(),//
                    MysqlJdbcConfig.instance.password());//
            String sql = "select c.name,c.key,c.value from config_common_info c   where c.name= ? or c.name = 'common'  union all select cc.name,cc.key,cc.value from  config_info cc where cc.name=? and cc.env = ? ";
           // String sql = "select c.name,c.key,c.value from config_common_info c   union all select cc.name,cc.key,cc.value from  config_info cc where cc.name=? and cc.env = ? ";
            List<ConfigInfo> configInfoList = queryRunner.query(connection, sql,new BeanListHandler<>(ConfigInfo.class),new Object[]{name,name,env} );
            ConfigInfo configInfo=null;
            if(configInfoList!=null&&configInfoList.size()>0){
                int size = configInfoList.size();
                for(int i=0;i<size;i++){
                    configInfo = configInfoList.get(i);
                    if(configInfo!=null){
                        String key = configInfo.getKey();
                        String value = configInfo.getValue();
                        if(StringUtils.isNotBlank(key)&&StringUtils.isNotBlank(value)){
                            if(excludeKeys!=null&&!excludeKeys.isEmpty()&&excludeKeys.contains(key)){
                               continue;
                            }
                            Object object = properties.get(key);
                            if(isOverride()){
                                if(object!=null){
                                    logger.warn("当前的配置中已经有key为{}的属性，数据库中值将覆盖此值",key);
                                }
                                properties.put(key, value);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if(connection!=null){
                try {
                    DbUtils.close(connection);
                } catch (SQLException e) {
                    logger.error("连接关闭异常:{},{}",e.getMessage(),e);
                } 
            }
            
        }
    }
    
    public static Connection getConnection(String driverClass,String url,String username,String password) throws SQLException, ClassNotFoundException{
        Class.forName(driverClass);
        return DriverManager.getConnection(url, username, password);
    }

    public boolean isOverride() {
        if(StringUtils.isBlank(MysqlJdbcConfig.instance.override())){
            return override;
        }
        return Boolean.valueOf(MysqlJdbcConfig.instance.override());
    }

    public void setOverride(boolean override) {
        this.override = override;
    }
    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getExcludeKeys() {
        return excludeKeys;
    }

    public void setExcludeKeys(Set<String> excludeKeys) {
        this.excludeKeys = excludeKeys;
    }
	
}
