package base.arch.component.exception.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import base.arch.component.exception.SystemServiceException;
import base.arch.component.exception.api.ExceptionManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class GlobalErrorServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4046842535574053035L;

	private static final String Exception = null;

	private Logger logger = LoggerFactory
			.getLogger(GlobalErrorServlet.class);

	private String errorPage="";
	
	private String beanName="";
	
	private ExceptionManager exceptionManager;
	
	public String getErrorPage() {
		return errorPage;
	}

	public void setErrorPage(String errorPage) {
		this.errorPage = errorPage;
	}
	public ExceptionManager getExceptionManager() {
		return exceptionManager;
	}

	public void setExceptionManager(ExceptionManager exceptionManager) {
		this.exceptionManager = exceptionManager;
	}


	@Override
	public void init() throws ServletException {
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());  
		beanName = this.getInitParameter("beanName");
		 exceptionManager =(ExceptionManager) wac.getBean("exceptionManager");
		if(StringUtils.isNotBlank(beanName)) {
			errorPage = (String) wac.getBean(beanName);
		} else {
			errorPage = this.getInitParameter("errorPage");
		}
		super.init();
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) {
		try {
			Object ex =  req.getAttribute("javax.servlet.error.exception");
			if(ex != null) {
				if(ex instanceof Exception) {
					Exception e = (java.lang.Exception) ex;
				   logger.error("ErrorServlet",e);
					exceptionManager.publish(e.getMessage(), "GlobalErrorServlet", SystemServiceException.SYS_ERROR_CODING , e);
				   e.printStackTrace();
				}
			}
			resp.sendRedirect(errorPage);
		} catch (Exception e) {
			logger.error("errorPage",e);
			e.printStackTrace();
		}
		return;
	}
}
