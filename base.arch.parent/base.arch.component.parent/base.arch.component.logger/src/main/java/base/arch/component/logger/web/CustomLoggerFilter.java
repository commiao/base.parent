package base.arch.component.logger.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import base.arch.component.logger.config.LoggerConfiguration;
import base.arch.component.logger.handler.HTLogger;
import base.arch.component.logger.manager.HTLoggerFactory;
import base.arch.component.logger.util.FilterSpringUtil;
import base.arch.component.logger.util.concurrent.LoggerInfo;
import base.arch.component.logger.util.concurrent.LoggerThreadContext;

public class CustomLoggerFilter extends OncePerRequestFilter {

	private static final String METHOD_GET = "GET";
	private static final String METHOD_POST = "POST";
/*    private static final String METHOD_DELETE = "DELETE";
    private static final String METHOD_HEAD = "HEAD";
    private static final String METHOD_OPTIONS = "OPTIONS";
    private static final String METHOD_PUT = "PUT";
    private static final String METHOD_TRACE = "TRACE";*/

    private HTLogger logger = HTLoggerFactory.getLogger(CustomLoggerFilter.class);
    UserInfoAgentService userInfoAgentService;

    String beanName = "userInfoAgentService";

    @Override
    protected void initFilterBean() throws ServletException {
        super.initFilterBean();
        beanName = this.getFilterConfig().getInitParameter("beanName");
        if (StringUtils.isBlank(beanName)) {
            beanName = "userInfoAgentService";
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            
            LoggerInfo loggerInfo = new LoggerInfo();
            loggerInfo.setThreadId(this.getUUID());
            loggerInfo.setClientIp(LoggerUtils.getIpAddr(request));
            loggerInfo.setSessionId( request.getSession().getId());
            loggerInfo.setRequsetURL(request.getRequestURI());
            loggerInfo.setServerIp(request.getLocalAddr());
            loggerInfo.setUserAgent( request.getHeader(LoggerConfiguration.USER_AGENT));
            loggerInfo.setStartTime(getCurDate());
            userInfoAgentService = getUserInfoAgent(request);
            if (userInfoAgentService != null) {
                loggerInfo.setUserInfo(StringUtils.trimToEmpty(userInfoAgentService.getUserInfo(request)));
            }
            ServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper(request);
            BodyReaderHttpServletResponseWrapper responseWrapper = new BodyReaderHttpServletResponseWrapper(response);
            if (METHOD_POST.equals(request.getMethod())) {
                String body = HttpHelper.getBodyString(requestWrapper);
                loggerInfo.setRequestParams(body);
                LoggerThreadContext.setAuditInfo(loggerInfo);
                logger.info(LoggerThreadContext.get().toString());
                filterChain.doFilter(requestWrapper, responseWrapper);
            } else {
                if (METHOD_GET.equals(request.getMethod())) {
                    loggerInfo.setRequestParams(request.getQueryString());
                }
                LoggerThreadContext.setAuditInfo(loggerInfo);
                logger.info(LoggerThreadContext.get().toString());
                filterChain.doFilter(requestWrapper, responseWrapper);
            }
            responseWrapper.flushBuffer();
            byte[] copy = responseWrapper.getCopy();
            String responseJson = new String(copy, responseWrapper.getCharacterEncoding());
            loggerInfo.setResponseData(responseJson);
            loggerInfo.setEndTime(getCurDate());
            LoggerThreadContext.setAuditInfo(loggerInfo);
            logger.info(LoggerThreadContext.get().toString());
        }finally {
            LoggerThreadContext.clear();
        }
    }

    private UserInfoAgentService getUserInfoAgent(HttpServletRequest request) {
        if (userInfoAgentService == null) {
            userInfoAgentService = FilterSpringUtil.getSpringBean(request, beanName, UserInfoAgentService.class);
        }
        return userInfoAgentService;
    }

    private String getUUID() {
        UUID uuid = UUID.randomUUID();
        String oid = uuid.toString().replace("-", "");
        return oid;
    }
    
    private String getCurDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return sdf.format(new java.util.Date());
    }
    
    

}
