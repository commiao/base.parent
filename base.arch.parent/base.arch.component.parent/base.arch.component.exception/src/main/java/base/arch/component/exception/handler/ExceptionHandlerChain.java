package base.arch.component.exception.handler;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import base.arch.component.exception.UnifiedException;
import base.arch.component.exception.vo.GlobalInfo;



/**
 * 
 * <Description>异常处理责任链<br> 
 * @see @see com.tuniu.common.exception.handler.AbsExceptionHandler
 * @author wanglei<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2015年4月13日 <br>
 */
public class ExceptionHandlerChain {
    /**
     * 异常处理类数组
     */
    private List<AbsExceptionHandler> chain = new LinkedList<AbsExceptionHandler>();
    

	private boolean asynFlag = false;
	
	
	
    
    /**
     * 当前下标
     */
    private ThreadLocal<Integer> index = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return Integer.valueOf(0);
        }
    };
    
    /**
     * 
     * Description:循环处理异常<br> 
     *  
     * @author wanglei<br>
     * @taskId <br>
     * @param ue
     * @param gi
     * @return <br>
     */
    public boolean handleException(final UnifiedException ue, final GlobalInfo gi) {
    	for (Iterator<AbsExceptionHandler> iterator = chain.iterator(); iterator.hasNext();) {
			final AbsExceptionHandler absExceptionHandler = (AbsExceptionHandler) iterator.next();
			if(asynFlag){
				gi.getExceptionHandleExecutor().execute(new Runnable() {
					@Override
					public void run() {
						absExceptionHandler.doHandle(ue, gi);
					}
				});
			}else{
				absExceptionHandler.doHandle(ue, gi);
			}
		}
       
        return true;
    }
    public boolean isAsynFlag() {
		return asynFlag;
	}

	public void setAsynFlag(boolean asynFlag) {
		this.asynFlag = asynFlag;
	}


    /** 
     * get chain
     * @return Returns the chain.<br> 
     */
    public List<AbsExceptionHandler> getChain() {
        return chain;
    }

    /** 
     * set chain
     * @param chain The chain to set. <br>
     */
    public void setChain(List<AbsExceptionHandler> chain) {
        this.chain = chain;
    }
    
}
