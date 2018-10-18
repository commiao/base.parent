package base.arch.component.spring;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import base.arch.tools.encrypt.aes.AESUtils;

import com.alibaba.fastjson.JSON;


/**
 * @author chenxin
 * 
 * 自定义 配置文件解析插件  读取 数据库中 配置表
 * 
 *
 */
public class CustomizedConfigDBPlugin implements IPropertyPlaceholderConfigurerPlugin {

	private  final Logger logger = LoggerFactory.getLogger(this.getClass());

	private boolean active = false;
	
	private String driverClassName;

	private String url;
	
	private String password;
	
	private String username;
	
	private String runSql ;
	
	private boolean aesc=false;
	
	private String aescCode=null;
	
	private String aescFilePath = null;
	
	private String aescValue;
	
	public boolean isAesc() {
		return aesc;
	}

	public void setAesc(boolean aesc) {
		this.aesc = aesc;
	}

	public String getAescCode() {
		return aescCode;
	}

	public void setAescCode(String aescCode) {
		this.aescCode = aescCode;
	}
	
	public String getAescFilePath() {
		return aescFilePath;
	}

	public void setAescFilePath(String aescFilePath) {
		this.aescFilePath = aescFilePath;
	}

	public String getRunSql() {
		return runSql;
	}

	public void setRunSql(String runSql) {
		this.runSql = runSql;
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
                                                                                                                                                                                                                                                                                                                                                                                                           
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public void loadConfig(Properties props) {
		
		if(aesc && active){
			try {
				this.resolveAescContent(props);
			} catch (Exception e) {
				logger.error("aesc contexnt is error",e);
			}
		}
		
		if(this.prepare()){
			if(props == null){
				logger.info("this props is null");
				return;
			}
			Connection conn = null;
			try {
				 Class.forName(this.driverClassName) ; 
				 logger.info("url = "+url);
				 conn = DriverManager.getConnection(url , username , password ) ;
				 Statement statament = conn.createStatement(); 
				 ResultSet resultSel =  statament.executeQuery(runSql);
				 while(resultSel.next()){
					 String key = StringUtils.trimToEmpty(resultSel.getString(1));
					 String value =StringUtils.trimToEmpty(resultSel.getString(2));
					 props.put(StringUtils.trimToEmpty(key) , StringUtils.trimToEmpty(value));
				 }
			} catch (Exception e) {
				logger.error("CustomizedConfigDBPlugin JDBC ERROR", e);;
			}finally{
				if(conn != null){
					try {
						conn.close();
					} catch (SQLException e) {
						logger.error("Connection is not close", e);;
					}	
				}
					
			}
		}
	}
	
	public boolean prepare(){
		
		if(!active){
			logger.info("this plugin no active");
			return false;
		}
		if(StringUtils.isBlank(this.driverClassName)){
			logger.info("driverClassName is null;");
			return false;
		}
		
		if(StringUtils.isBlank(this.url)){
			logger.info("dburl is null;");
			return false;
		}
		
		if(StringUtils.isBlank(this.username)){
			logger.info("username is null;");
			return false;
		}
		
		if(StringUtils.isBlank(this.password)){
			logger.info("password is null;");
			return false;
		}
		if(StringUtils.isBlank(this.runSql)){
			logger.info("runSql is null;");
			return false;
		}
		return true;
	}
	
	
	
	public void resolveAescContent(Properties props) throws Exception{
		byte[] aescByteArr = null;
		if(StringUtils.isNotBlank(this.getAescFilePath())) {
			File file = new File(this.getAescFilePath());
			if(file.exists()) {
				aescByteArr = FileUtils.readFileToByteArray(file);
			} else {
				aescByteArr = getDefaultAesc(props);
			}
		} else {
			aescByteArr = getDefaultAesc(props);
		}
		
		String jsonMessage = new String(AESUtils.getInstance().decrypt(AESUtils.asByte(new String(aescByteArr))));

		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) jsonStringToJavaObject(jsonMessage, HashMap.class) ;
		this.driverClassName = map.get("driverClassName");
		this.url = map.get("url");
		this.password = map.get("password");
		this.username = map.get("username");
		this.runSql = map.get("runSql");
	}
	
	private byte[] getDefaultAesc(Properties props) {
		this.aescValue = (String) props.get(this.getAescCode());
		return this.aescValue.getBytes();
	}
	
	private <T> Object jsonStringToJavaObject(String str, Class<T> clazz) {
		if (str.indexOf("[") != -1) {
			str = str.replace("[", "");
		}
		if (str.indexOf("]") != -1) {
			str = str.replace("]", "");
		}
		return ((T) JSON.parseObject(str, clazz));
	}
	
}
