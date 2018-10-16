package base.arch.component.exception.handler;

import base.arch.component.exception.UnifiedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import base.arch.component.exception.vo.GlobalInfo;



/**
 * <Description>打印logback日志文件<br>
 * 
 * @author chenxin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2015年4月13日 <br>
 */
public class LogBackExceptionHandler extends AbsExceptionHandler {
    // logger
    private Logger LOG = LoggerFactory.getLogger(LogBackExceptionHandler.class);
    
 
    @Override
    protected void doHandle(UnifiedException ue, GlobalInfo gi) {
        LOG.error(
                "系统code:" + gi.getSystemCode() + " 系统名称:" + gi.getSystemName() + " 业务模块:" + ue.getBusinessModule() + " 发生异常,异常描述:"
                        + ue.getDescription(), ue);
    }

	@Override
	public boolean supports(UnifiedException ue) {
		return true;
	}

}
