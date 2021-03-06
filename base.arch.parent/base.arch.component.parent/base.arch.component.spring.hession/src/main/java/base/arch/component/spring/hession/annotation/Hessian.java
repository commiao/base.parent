package base.arch.component.spring.hession.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
 
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Hessian {
 
    String description() default ""; //描述
 
    boolean overloadEnabled() default false; // 是否支持方法重载
     
    String uri()  ; // 用于服务端bean名称，也是客户端访问链接的后半部分 配置。如: /helloService
 
    Context context() default Context.DEFALUT_URL_PRE; // 客户端访问链接前半部分配置 如 http://localhost:8080/
}