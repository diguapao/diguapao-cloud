package org.diguapao.cloud.framework.spring.aop.customannotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 分布式锁注解
 * <p>
 * 被此注解修饰的方法将会执行“获取锁->执行方法->释放锁”这样一个逻辑
 *
 * @author DiGuaPao
 * @version 2024.10.22
 * @since 2024-10-22 08:39:35
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DistributedLock {

    /**
     * 锁使用的key
     */
    String lockKey();

    /**
     * 锁超时释放时间，单位：秒
     */
    long timeout();

    /**
     * 当获取不到锁时是否中断程序继续执行
     */
    boolean interrupt() default false;
}
