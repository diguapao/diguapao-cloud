package org.diguapao.cloud.framework.spring.aop.aspect;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.diguapao.cloud.framework.spring.aop.customannotations.DistributedLock;
import org.diguapao.cloud.framework.spring.aop.exception.DistributedLockException;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁注解{@link org.diguapao.cloud.framework.spring.aop.customannotations.DistributedLock}处理切面
 *
 * @author DiGuaPao
 * @version 2024.10.22
 * @since 2024-10-22 08:39:35
 */
@Slf4j
@Aspect
@Component
public class DistributedLockAspect {
    @Resource
    private RedissonClient redissonClient;

    @Around("@annotation(distributedLock)")
    public Object measureElapsedTime(ProceedingJoinPoint joinPoint, DistributedLock distributedLock) throws Throwable {
        Object result = null;
        RLock fairLock = null;
        boolean lockSuccess = false;
        String fullMethodName = null;
        try {
            Assert.isTrue(StringUtils.isNotBlank(distributedLock.lockKey()), () -> new DistributedLockException("锁使用的key不可为空"));
            Assert.isTrue(distributedLock.timeout() > 0, () -> new DistributedLockException("锁超时时间必须大于0"));
            String appName = "examples-spring-aop";
            fairLock = redissonClient.getFairLock(appName + StrUtil.COLON + distributedLock.lockKey());
            lockSuccess = fairLock.tryLock(3, 300, TimeUnit.SECONDS);

            // 切点方法的全限定名
            fullMethodName = joinPoint.getSignature().getDeclaringTypeName() + StrUtil.DOT + joinPoint.getSignature().getName();

            if (lockSuccess) {
                log.debug("操作（{}）尝试获取分布式锁成功", fullMethodName);
                result = joinPoint.proceed();
            } else if (distributedLock.interrupt()) {
                throw new DistributedLockException("操作（{}）尝试获取分布式锁失败", fullMethodName);
            } else {
                log.warn("操作（{}）尝试获取分布式锁失败", fullMethodName);
            }
        } finally {
            if (Objects.nonNull(fairLock) && fairLock.isHeldByCurrentThread() && lockSuccess) {
                fairLock.unlock();
                log.debug("操作（{}）尝试释放分布式锁成功", fullMethodName);
            }
        }
        return result;
    }

}
