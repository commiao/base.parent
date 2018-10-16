package base.arch.component.exception.handler;

import base.arch.component.exception.UnifiedException;
import base.arch.component.exception.vo.GlobalInfo;


/**
 * <Description>异常处理抽象类<br>
 * 
 * @author wanglei<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2015年4月13日 <br>
 */
public abstract class AbsExceptionHandler implements ExceptionHandler {
    /**
     * boolean flag of Asyn
     */
    private boolean asynFlag = false;
    
    /**
     * 
     * Description:执行异常处理<br> 
     *  
     * @author wanglei<br>
     * @taskId <br>
     * @param ue 统一异常
     * @param gi 异常处理全局信息
     * @return <br>
     */
    protected abstract void doHandle(UnifiedException ue, GlobalInfo gi);

}
