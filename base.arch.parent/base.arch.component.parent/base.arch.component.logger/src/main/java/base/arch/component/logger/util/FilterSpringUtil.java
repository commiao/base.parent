package base.arch.component.logger.util;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class FilterSpringUtil {

	private static final Logger logger = LoggerFactory
			.getLogger(FilterSpringUtil.class);

	public static <T> T getSpringBean(final HttpServletRequest httpRequest,
			final String beanName, final Class<T> beanClass) {
		final HttpSession session = httpRequest.getSession();
		final ServletContext servletContext = session.getServletContext();
		return getSpringBean(servletContext, beanName, beanClass);
	}

	
	@SuppressWarnings("unchecked")
	public static <T> T getSpringBean(final ServletContext servletContext,
			final String beanName, final Class<T> beanClass) {
		T ret = null;
		final WebApplicationContext appContext = WebApplicationContextUtils
				.getRequiredWebApplicationContext(servletContext);

		if (StringUtils.isNotBlank(beanName)) {
			try {
				ret = (T) appContext.getBean(beanName);
			} catch (final NoSuchBeanDefinitionException nsbde) {
				logger.warn("No bean found with the specified name. Trying to resolve bean using type...");
			}
		}
		if (ret == null) {
			if (beanClass == null) {
				logger.warn("No bean could be resolved. Reason: No type specified.");
			} else {
				final Map<String, T> beansOfType = appContext
						.getBeansOfType(beanClass);
				if (beansOfType != null && !beansOfType.isEmpty()) {
					if (beansOfType.size() > 1) {
						logger.warn("More than one matching bean found of type "
								+ beanClass.getSimpleName()
								+ ". Returning the first one found.");
					}
					ret = beansOfType.values().iterator().next();
				}
			}
		}
		return ret;
	}
}
