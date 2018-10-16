package base.arch.component.exception;


/**
 * 
 * <Description>统一的异常定义,默认异常级别是轻微 @see ExceptionLevel<br> 
 *  
 * @author wanglei<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2015年4月13日 <br>
 */
public class UnifiedException extends RuntimeException{

    public UnifiedException(){}
    
    /**
     * serialVersionUID <br>
     */
    private static final long serialVersionUID = 19930304L;
    
    /**
     * 异常描述
     */
    private String description = "";
    /**
     * 业务模块
     */
    private String businessModule = "";
    
    /**
     * 自定义errorCode,可根据这个errorCode做异常的筛选、特殊处理
     */
    private String errorCode = "99999";
    
    /**
     * 是否已处理过
     */
    private boolean isHandled = false;
    
    private String exceptionType = "";

	private Object dateModel;
    
	
	public String getExceptionType() {
		return exceptionType;
	}

	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}
	
    /**
     * 构造统一异常
     * @param level 异常级别 
     * @param description 异常描述
     * @param businessModule 异常业务模块
     * @param errorCode 异常errorCode
     * @param cause the throwable which caused this UnifiedException
     */
    public UnifiedException(String description,String businessModule,String errorCode,Throwable cause) {
        super(description,cause);
        this.errorCode = errorCode;
        this.description = description;
        this.businessModule = businessModule;
    }
    
    /**
     * 构造统一异常
     * @param level 异常级别 
     * @param description 异常描述
     * @param errorCode 异常errorCode
     * @param cause the throwable which caused this UnifiedException
     */
    public UnifiedException(String description,String errorCode,Throwable cause) {
        super(description,cause);
        this.errorCode = errorCode;
        this.description = description;
    }
  
    public UnifiedException(String description,String errorCode,Object dataModel ,Throwable cause) {
        super(description,cause);
        this.errorCode = errorCode;
        this.description = description;
        this.dateModel = dataModel;
    }

  
    /** 
     * get description
     * @return Returns the description.<br> 
     */
    public String getDescription() {
        return description;
    }

    /** 
     * set description
     * @param description The description to set. <br>
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /** 
     * get businessModule
     * @return Returns the businessModule.<br> 
     */
    public String getBusinessModule() {
        return businessModule;
    }

    /** 
     * set businessModule
     * @param businessModule The businessModule to set. <br>
     */
    public void setBusinessModule(String businessModule) {
        this.businessModule = businessModule;
    }

    /** 
     * get errorCode
     * @return Returns the errorCode.<br> 
     */
    public String getErrorCode() {
        return errorCode;
    }

    /** 
     * set errorCode
     * @param errorCode The errorCode to set. <br>
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /** 
     * get isHandled
     * @return Returns the isHandled.<br> 
     */
    public boolean isHandled() {
        return isHandled;
    }

    /** 
     * set isHandled
     * @param isHandled The isHandled to set. <br>
     */
    public void setHandled(boolean isHandled) {
        this.isHandled = isHandled;
    }

	public Object getDateModel() {
		return dateModel;
	}

	public void setDateModel(Object dateModel) {
		this.dateModel = dateModel;
	}
    
}
