package base.arch.component.auditLogger.stereotype;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuditLog {

    /**
     * 是否记录参数
     */
    boolean isRecordArgs() default true;
    /**
     * 是否记录返回信息
     */
    boolean isRecordReturn() default true;

    RecordStrategy recordStrategy () default RecordStrategy.ALL; 

    /**
     * 异常策略
     */
    enum RecordStrategy {
        ALL,//记录所有，包含异常信息
        EXCEPTION//只记录异常信息
    }

}