package base.arch.component.exception.aop;

import java.lang.reflect.Method;

import base.arch.component.exception.SystemServiceException;
import base.arch.component.exception.UnifiedException;
import base.arch.component.exception.api.ExceptionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.ThrowsAdvice;

import base.arch.component.exception.ServiceException;


/**
 * 
 * <Description>切面统一异常处理,仅处理UnifiedException,若使用该切面配置，请在业务代码里throw出UnifiedException<br> 
 *  
 * @author xchen<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2015年4月20日 <br>
 */
public class UnifiedExceptionThrowsAdvice implements ThrowsAdvice {
    /**
     * logger
     */
    private Logger logger = LoggerFactory.getLogger(UnifiedExceptionThrowsAdvice.class);
    /**
     * 异常处理器
     */
    private ExceptionManager exceptionManager;
    /**
     * 
     * Description:对于统一异常的处理<br> 
     *  
     * @author xchen<br>
     * @taskId <br>
     * @param method
     * @param args
     * @param target
     * @param ex
     * @throws Throwable <br>
     */
    public void afterThrowing(Method method, Object[] args, Object target,
            Exception ex) throws Throwable {
    	String syscode = getExceptionManager().getSystemCode();
    	StringBuffer stb = new StringBuffer();
    	stb.append(target.getClass().getName());
    	stb.append("-");
    	stb.append(method.getName());
    	if(ex instanceof UnifiedException) {
			UnifiedException ue = (UnifiedException) ex;
			ue.setBusinessModule(stb.toString());
			ue.setErrorCode(syscode + ue.getErrorCode());
			exceptionManager.publishAndThrow(ue);
			throw ue;
		} else if (ex instanceof ServiceException) {
			ServiceException se = (ServiceException)ex;
			exceptionManager.publish(se.getDescription(), stb.toString(), syscode + se.getErrorCode(), ex);
			throw se;
		}else{
			exceptionManager.publish(ex.getMessage(), stb.toString(), SystemServiceException.SYS_ERROR_CODING , ex);
        	throw ex;
		}
    }
    /** 
     * get exceptionManager
     * @return Returns the exceptionManager.<br> 
     */
    public ExceptionManager getExceptionManager() {
        return exceptionManager;
    }
    /** 
     * set exceptionManager
     * @param exceptionManager The exceptionManager to set. <br>
     */
    public void setExceptionManager(ExceptionManager exceptionManager) {
        this.exceptionManager = exceptionManager;

    }
    
}
