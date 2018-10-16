package base.arch.component.exception.aop;

import java.lang.reflect.Method;

import base.arch.component.exception.ServiceException;
import base.arch.component.exception.SystemServiceException;
import base.arch.component.exception.UnifiedException;
import base.arch.component.exception.api.ExceptionManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

public class UnifiedExceptionAroundAdvice {

	private ExceptionManager exceptionManager;

	public void doAround(ProceedingJoinPoint pjp) throws Throwable {

		String syscode = getExceptionManager().getSystemCode();

		StringBuffer stb = new StringBuffer();
		// 调用方法的参数
		Object[] args = pjp.getArgs();
		// 调用的方法名
		MethodSignature signature = (MethodSignature) pjp.getSignature();
		Method method = signature.getMethod();
		String targetName = pjp.getTarget().getClass().getName();
		stb.append(targetName);
		stb.append("-");
		stb.append(method);

		// 执行完方法的返回值：调用proceed()方法，就会触发切入点方法执行
		try {
		    if(void.class.equals(method.getReturnType())){
		        pjp.proceed();
		    }else{
		        Object result = pjp.proceed(); 
		    }
		} catch (Exception ex) {
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

	}

	public ExceptionManager getExceptionManager() {
		return exceptionManager;
	}

	public void setExceptionManager(ExceptionManager exceptionManager) {
		this.exceptionManager = exceptionManager;
	}

}
