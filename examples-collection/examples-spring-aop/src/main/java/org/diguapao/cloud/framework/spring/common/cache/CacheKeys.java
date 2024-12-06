package org.diguapao.cloud.framework.spring.common.cache;


/**
 * 缓存 key 定义
 *
 * @author DiGuaPao
 * @version 2023.0.1
 * @since 2024-05-27 14:22:24
 */
public interface CacheKeys {

    interface DistributedLockKeys {
        String AOP_CALCULATE_SERVICE_DIVIDE = "examples-spring-aop:aop:calculateService:divide";
    }

}