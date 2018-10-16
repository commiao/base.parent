package base.arch.component.mongo.entity;

import java.io.Serializable;

public class UserInfo implements Serializable{
	
	private static final long serialVersionUID = 2354570724123974564L;
	
	private String userName;  
    private String password;
    
	public UserInfo(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}
	
	
	
	public UserInfo() {
		super();
	}



	/**
	 * 获取 #{bare_field_comment}
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * 设置 #{bare_field_comment} 
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * 获取 #{bare_field_comment}
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 设置 #{bare_field_comment} 
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
    
}
