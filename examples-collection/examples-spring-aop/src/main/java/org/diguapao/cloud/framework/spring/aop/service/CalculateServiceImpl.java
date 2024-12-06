package org.diguapao.cloud.framework.spring.aop.service;

import lombok.extern.slf4j.Slf4j;
import org.diguapao.cloud.framework.spring.aop.customannotations.DistributedLock;
import org.springframework.stereotype.Service;

import static org.diguapao.cloud.framework.spring.common.cache.CacheKeys.DistributedLockKeys.AOP_CALCULATE_SERVICE_DIVIDE;

/**
 * 计算服务实现
 *
 * @author DiGuaPao
 * @version 2024.10.22
 * @since 2024-10-22 15:29:16
 */
@Slf4j
@Service
public class CalculateServiceImpl implements CalculateService {

    @Override
    @DistributedLock(lockKey = AOP_CALCULATE_SERVICE_DIVIDE, timeout = 3L)
    public int divide(int x, int y) {
        log.info("=========== CalculateService 被调用了");
        int result = x / y;
        log.info("=========== CalculateService 调用成功");
        return result;
    }

}

