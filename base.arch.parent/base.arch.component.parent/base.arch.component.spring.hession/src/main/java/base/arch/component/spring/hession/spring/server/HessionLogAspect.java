package base.arch.component.spring.hession.spring.server;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import base.arch.component.spring.hession.annotation.Hessian;

public class HessionLogAspect {
   private final Logger logger =LoggerFactory.getLogger(HessionLogAspect.class);
    
    
    public void doAroundMethod(ProceedingJoinPoint joinPoint, Hessian hessian) throws Throwable {
        logger.info("****************************注解解释器执行开始*********************************");

        //获取目标对象对应的类名
        logger.info("应用注解的类名："+joinPoint.getTarget().getClass());

        //获取实现类继承的接口名
        Class<?>[] c = joinPoint.getTarget().getClass().getInterfaces();
        logger.info("注解对象所实现的接口名："+c[0]);

        //获取到了注解在这个service实现类上的annotation
        Annotation[] a = joinPoint.getTarget().getClass().getAnnotations();

        //获取这个类上的注解的个数
        logger.info("应用注解类上的注解个数："+a.length);

        //判断这个类上面的注释是否是AnnotationName这个自定义的注解，如果是返回这个注解，如果不是返回null
        if(joinPoint.getTarget().getClass().getAnnotation(Hessian.class)!=null){
            //获取到这个类上的注解
            Hessian anns = joinPoint.getTarget().getClass().getAnnotation(Hessian.class);
             //输出这个类上的注解的值
            logger.info("注释在实现类上的annotation："+anns.uri());
        }

        //判断这个接口上是否存在此注解
        if(c[0].getAnnotation(Hessian.class)!=null){
            Hessian an = (Hessian) c[0].getAnnotation(Hessian.class);
            logger.info("注解对象所实现接口上的注解值："+an.uri());
        }

        //获取目标对象上正在执行的方法名
        String methodString = joinPoint.getSignature().getName();
        logger.info("目标对象上正在执行的方法名："+methodString);

        //获取到这个类上面的方法全名
        Method meths[] = joinPoint.getSignature().getDeclaringType().getMethods();
        logger.info("方法上面的全名："+meths[0]);

        //获取到这个类上面的方法上面的注释
//        Annotation[] anns = meths[0].getDeclaredAnnotations();//non
//        logger.info("正在执行方法上面的注释："+((Hessian)anns[0]).uri());

        //让你注释的那个方法执行
        /*joinPoint.proceed();*/ //@Around

        //完毕
        logger.info("****************************注解解释器执行完毕*********************************");
    }
}
