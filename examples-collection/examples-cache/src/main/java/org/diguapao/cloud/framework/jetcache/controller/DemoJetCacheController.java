package org.diguapao.cloud.framework.jetcache.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alicp.jetcache.anno.Cached;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Demo JetCache
 *
 * @author DiGuaPao
 * @version 1.0
 * @since 2024-12-09 20:35:34
 */
@Slf4j
@RestController
public class DemoJetCacheController {
    interface JetCacheKey {
        interface DemoJetCache {
            String DEMO_RT_CACHE_FOR_WRITE = "examples-cache:rt:demoRtCache";
            String DEMO_SRT_CACHE_FOR_WRITE = "examples-cache:srt:demoSrtCache";
            String DEMO_RC_CACHE_FOR_WRITE = "examples-cache:rc:demoRcCache";
            String DEMO_JET_CACHE_FOR_WRITE = "examples-cache:jet:demoJetCache";
            String DEMO_SDC_CACHE_FOR_WRITE = "examples-cache:sdc:demoSdcCache";
        }
    }

    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RedissonClient redissonClient;

    /**
     * Demo JetCache
     *
     * @author DiGuaPao
     * @since 2024-12-09 20:35:34
     */
    @PostMapping(value = "/demoCache/jet/{key}")
    public ResponseEntity<String> demoJetCache(@PathVariable("key") String key, @RequestParam(value = "val", required = false) String val) {
        val = SpringUtil.getBean(getClass()).doDemoJetCache(key, StrUtil.isBlank(val) ? "Demo JetCache Successful" : val);
        return ResponseEntity.ok(val);
    }

    /**
     * Demo Spring Data Cache
     *
     * @author DiGuaPao
     * @since 2024-12-09 20:35:34
     */
    @PostMapping(value = "/demoCache/sdc/{key}")
    public ResponseEntity<String> demoSdcCache(@PathVariable("key") String key, @RequestParam(value = "val", required = false) String val) {
        val = SpringUtil.getBean(getClass()).doDemoSdcCache(key, StrUtil.isBlank(val) ? "Demo Spring Data Cache Successful" : val);
        return ResponseEntity.ok(val);
    }

    /**
     * Demo RedisTemplate Cache
     *
     * @author DiGuaPao
     * @since 2024-12-09 20:35:34
     */
    @PostMapping(value = "/demoCache/rt/{key}")
    public ResponseEntity<String> demoRtCache(@PathVariable("key") String key, @RequestParam(value = "val", required = false) String val) {
        Set<String> cache = null;
        String cacheVal = null;
        String cacheKey = JetCacheKey.DemoJetCache.DEMO_RT_CACHE_FOR_WRITE + StrUtil.COLON + key;
        if (CollUtil.isEmpty(cache = (Set<String>) redisTemplate.opsForSet().members(cacheKey))) {
            if (StrUtil.isBlank(val)) {
                cacheVal = val = "Demo RedisTemplate Cache Successful";
            }
            redisTemplate.opsForSet().add(cacheKey, cacheVal);
            log.warn("rt 缓存取数为空，设置缓存完成：key={}，val={}", key, val);
            cache = (Set<String>) redisTemplate.opsForSet().members(cacheKey);
        } else {
            log.info("rt 缓存取数：key={}，val={}", key, cache.iterator().next());
        }
        return ResponseEntity.ok(cache.iterator().next());
    }

    /**
     * Demo StringRedisTemplate Cache
     *
     * @author DiGuaPao
     * @since 2024-12-09 20:35:34
     */
    @PostMapping(value = "/demoCache/srt/{key}")
    public ResponseEntity<String> demoSrtCache(@PathVariable("key") String key, @RequestParam(value = "val", required = false) String val) {
        Object naVal = null;
        if (Objects.isNull(naVal = stringRedisTemplate.opsForValue().get(JetCacheKey.DemoJetCache.DEMO_SRT_CACHE_FOR_WRITE + StrUtil.COLON + key))) {
            if (StrUtil.isBlank(val)) {
                naVal = val = "Demo StringRedisTemplate Cache Successful";
            }
            stringRedisTemplate.opsForValue().append(JetCacheKey.DemoJetCache.DEMO_SRT_CACHE_FOR_WRITE + StrUtil.COLON + key, val);
            log.warn("srt 缓存取数为空，设置缓存完成：key={}，val={}", key, val);
        } else {
            log.info("srt 缓存取数：key={}，val={}", key, naVal);
        }
        return ResponseEntity.ok((String) naVal);
    }

    /**
     * Demo RedissonClient Cache
     *
     * @author DiGuaPao
     * @since 2024-12-09 20:35:34
     */
    @PostMapping(value = "/demoCache/rc/{key}")
    public ResponseEntity<String> demoRcCache(@PathVariable("key") String key, @RequestParam(value = "val", required = false) String val) {
        RBucket<String> bucket = redissonClient.getBucket(JetCacheKey.DemoJetCache.DEMO_RC_CACHE_FOR_WRITE + StrUtil.COLON + key);
        Object naVal = null;
        if (Objects.isNull(naVal = bucket.get())) {
            if (StrUtil.isBlank(val)) {
                naVal = val = "Demo RedissonClient Cache Successful";
            }
            bucket.set(val);
            bucket.expireAsync(Duration.ofHours(1));
            log.warn("rc 缓存取数为空，设置缓存完成：key={}，val={}", key, val);
        } else {
            log.info("rc 缓存取数：key={}，val={}", key, naVal);
        }
        return ResponseEntity.ok((String) naVal);
    }

    @Cached(name = JetCacheKey.DemoJetCache.DEMO_JET_CACHE_FOR_WRITE, key = "#key", condition = "#key != null and #key != ''", expire = 1, timeUnit = TimeUnit.HOURS, postCondition = "result != null and #key != null and #key != ''")
    public String doDemoJetCache(String key, String val) {
        if (StrUtil.isBlank(key)) {
            val = val;
        }
        log.info("jet 未走缓存取数：key={}，val={}", key, val);
        return val;
    }

    @Cacheable(value = JetCacheKey.DemoJetCache.DEMO_SDC_CACHE_FOR_WRITE, key = "#key", condition = "#key != null and #key != ''", unless = "#result == null or #key == null or #key == ''", cacheManager = "redisCacheManager")
    public String doDemoSdcCache(String key, String val) {
        if (StrUtil.isBlank(key)) {
            val = val;
        }
        log.info("sdc 未走缓存取数：key={}，val={}", key, val);
        return val;
    }

}
