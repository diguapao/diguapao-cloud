package org.diguapao.cloud.framework.spring.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 计算切面
 *
 * @author DiGuaPao
 * @version 2024.10.22
 * @since 2024-10-22 15:29:16
 */
@Aspect
@Component
public class CalculateAspect {

    private static final Logger log = LoggerFactory.getLogger(CalculateAspect.class);

    @Pointcut("execution(* org.diguapao.cloud.framework.spring.aop.service.CalculateService.*(..))")
    private void pointcut() {
        log.info("********** @Pointcut 切入点");
    }

    @Before("pointcut()")
    public void before() {
        log.info("********** @Before 前置通知");
    }

    @After("pointcut()")
    public void after() {
        log.info("******** @After 后置通知");
    }

    @AfterReturning("pointcut()")
    public void afterReturning() {
        log.info("******* @AfterReturning 返回通知");
    }

    @AfterThrowing("pointcut()")
    public void afterThrowing() {
        log.error("******** @AfterThrowing 异常通知");
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result;
        log.info("环绕通知之前");
        result = proceedingJoinPoint.proceed();
        log.info("环绕通知之后");
        return result;
    }

}