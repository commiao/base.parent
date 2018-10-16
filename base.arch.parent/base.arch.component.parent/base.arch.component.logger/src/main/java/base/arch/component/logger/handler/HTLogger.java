package base.arch.component.logger.handler;

import java.io.Serializable;

public interface HTLogger extends Serializable{

    /**
     * 输出错误信息
     *
     * @param e 异常信息
     */
    public void debug(Throwable e);

    /**
     * 输出错误信息
     *
     * @param msg 信息内容
     * @param e 异常信息
     */
    public void debug(String msg, Throwable e);
    
    public void debug(String format, Object... arguments);
    /**
     * 输出错误信息
     *
     * @param e 异常信息
     */
    public void error(Throwable e);

    /**
     * 输出错误信息
     *
     * @param msg 信息内容
     * @param e 异常信息
     */
    public void error(String msg, Throwable e);
    
    public void error(String format, Object... arguments);
    
    /**
     * 输出普通信息
     *
     * @param msg 信息内容
     */
    public void info(String msg);

    /**
     * 输出普通信息
     *
     * @param e 异常信息
     */
    public void info(Throwable e);
    
    /**
     * 输出普通信息
     *
     * @param msg 信息内容
     * @param e 异常信息
     */
    public void info(String msg, Throwable e);
    
    public void info(String format, Object... arguments);
    /**
     * 输出普通信息
     *
     * @param msg 信息内容
     */
    public void warn(String msg);
    
    /**
     * 输出普通信息
     *
     * @param e 异常信息
     */
    public void warn(Throwable e);
    
    /**
     * 输出普通信息
     *
     * @param msg 信息内容
     * @param e 异常信息
     */
    public void warn(String msg, Throwable e);
    
    public void warn(String format, Object... arguments);
    
    
    
}
