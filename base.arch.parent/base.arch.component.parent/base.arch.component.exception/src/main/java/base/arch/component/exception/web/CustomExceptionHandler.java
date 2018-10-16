package base.arch.component.exception.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import base.arch.component.exception.utils.ExceptionHandleUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import base.arch.component.exception.api.ExceptionManager;

public class CustomExceptionHandler implements HandlerExceptionResolver {

	private Logger logger = LoggerFactory
			.getLogger(CustomExceptionHandler.class);

	private int type = 0;

	private String path = "/errorServlet";
	
	private ExceptionManager exceptionManager;

	public ExceptionManager getExceptionManager() {
		return exceptionManager;
	}

	public void setExceptionManager(ExceptionManager exceptionManager) {
		this.exceptionManager = exceptionManager;
	}

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object arg2, Exception ex) {
		logger.error("CustomExceptionHandler.resolveException", ex);
		ExceptionHandleUtils.handle(ex, exceptionManager);
		ModelAndView mv = new ModelAndView();
		try {
			if(type == 1){
				response.sendRedirect(path);
				return null;
			}
			if(type == 0){
				request.getRequestDispatcher(path).forward(request, response);
				return null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return mv;
	}
}
