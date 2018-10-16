package base.arch.component.logger.manager;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

import base.arch.component.logger.bean.Group;
import base.arch.component.logger.util.concurrent.LoggerInfo;
import base.arch.component.logger.util.concurrent.LoggerThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
/**
 *日志处理管理器
 * @author jannal
 */
public class HTLoggerManager {

    public final static Logger logger = LoggerFactory.getLogger(HTLoggerManager.class);
    /**
     * 是否异步
     */
    private boolean asynFlag = false;
    
    private static final int THREAD_NUM = Runtime.getRuntime().availableProcessors();
    
    /**
     *倍数
     */
    private int times = 1;
    
    private HTLoggerChain loggerChain;
    
    
    
    public HTLoggerManager(int times,boolean asynFlag){
        this.asynFlag = asynFlag;
        this.times = times;
        if(getAsynFlag()){
            if(logger.isDebugEnabled()){
                logger.debug("日志处理初始化线程池");
            }
            initThreadPool(); 
        }
        
    }
    private ThreadPoolExecutor theadPoolExecutor;
    
    public void initThreadPool(){
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("HLoggerManager-execute-%d").build();
        if(logger.isDebugEnabled()){
            logger.debug("日志处理线程倍数:{}",getTimes());
        }
        this.theadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(THREAD_NUM*getTimes(),threadFactory);
    }
    
    public void execute(Group group){
        LoggerInfo loggerInfo = LoggerThreadContext.get();
        String threadId = loggerInfo.getThreadId();
        group.setThreadId(threadId);
        if(getAsynFlag()){
            if(logger.isDebugEnabled()){
                logger.debug("日志处理开启异步执行............");
            }
            aysnExecute(group);
        }else{
            if(logger.isDebugEnabled()){
                logger.debug("日志处理开启同步执行............");
            }
            syncExecute(group);
        }
       
    }

    private void syncExecute(Group group) {
        try {
            loggerChain.handleRequest(group);
        }catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new RuntimeException(e.getMessage(),e);
        }
    }

    private void aysnExecute(Group group) {
        Future<Boolean> future = this.theadPoolExecutor.submit(new Callable<Boolean>() {
            public Boolean call() throws Exception {
                syncExecute(group);
                return true;
            }
        });
        if(future!=null){
            try {
                Boolean flag = future.get();
                logger.info("输出测试：{}", flag);
            } catch (InterruptedException e) {
                logger.error(e.getMessage(),e);
            } catch (ExecutionException e) {
                logger.error(e.getMessage(),e);
            }
        }
        printThreadPoolMonitoringInformation();
    }
    
    

    public void printThreadPoolMonitoringInformation() {
        logger.info("--------------线程池监控开始-------------");
        logger.info("线程池需要执行的任务数量:{}",this.theadPoolExecutor.getTaskCount());
        logger.info("线程池已经完成的任务数量:{}",this.theadPoolExecutor.getCompletedTaskCount());
        logger.info("线程池曾经创建过的最大线程数量:{}",this.theadPoolExecutor.getLargestPoolSize());
        logger.info("线程池的线程数量:{}",this.theadPoolExecutor.getPoolSize());
        logger.info("线程池活动的线程数:{}",this.theadPoolExecutor.getActiveCount());
        logger.info("内部线程池最小线程数:{}",this.theadPoolExecutor.getCorePoolSize());
        logger.info("--------------线程池监控结束-------------");
    }

    
    
    public HTLoggerChain getLoggerChain() {
        return loggerChain;
    }

    public void setLoggerChain(HTLoggerChain loggerChain) {
        this.loggerChain = loggerChain;
    }

    public boolean getAsynFlag() {
        return asynFlag;
    }

    public void setAsynFlag(boolean asynFlag) {
        this.asynFlag = asynFlag;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public ThreadPoolExecutor getTheadPoolExecutor() {
        return theadPoolExecutor;
    }

    public void setTheadPoolExecutor(ThreadPoolExecutor theadPoolExecutor) {
        this.theadPoolExecutor = theadPoolExecutor;
    }
}
