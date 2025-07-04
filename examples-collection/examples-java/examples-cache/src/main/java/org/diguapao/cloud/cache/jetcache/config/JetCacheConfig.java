package org.diguapao.cloud.cache.jetcache.config;

import com.alicp.jetcache.CacheBuilder;
import com.alicp.jetcache.anno.CacheConsts;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import com.alicp.jetcache.anno.support.GlobalCacheConfig;
import com.alicp.jetcache.anno.support.JetCacheBaseBeans;
import com.alicp.jetcache.embedded.EmbeddedCacheBuilder;
import com.alicp.jetcache.embedded.LinkedHashMapCacheBuilder;
import com.alicp.jetcache.redisson.RedissonCacheBuilder;
import com.alicp.jetcache.support.Fastjson2KeyConvertor;
import com.alicp.jetcache.support.JavaValueDecoder;
import com.alicp.jetcache.support.JavaValueEncoder;
import com.google.common.collect.Maps;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

import java.util.Map;

/**
 * JetCache 配置类
 *
 * @author DiGuaPao
 * @version 1.0
 * @since 2024/12/03
 */
@Configuration
@EnableMethodCache(basePackages = "org.diguapao.cloud.framework.jetcache")
@Import(JetCacheBaseBeans.class)
public class JetCacheConfig {
    @Autowired
    private Environment environment;

    @Bean
    public GlobalCacheConfig config(RedissonClient redissonClient) {
        Map<String, CacheBuilder> localBuilders = Maps.newHashMap();
        EmbeddedCacheBuilder<LinkedHashMapCacheBuilder.LinkedHashMapCacheBuilderImpl> localBuilder = LinkedHashMapCacheBuilder
                .createLinkedHashMapCacheBuilder()
                .keyConvertor(Fastjson2KeyConvertor.INSTANCE);
        localBuilders.put(CacheConsts.DEFAULT_AREA, localBuilder);

        Map<String, CacheBuilder> remoteBuilders = Maps.newHashMap();
        RedissonCacheBuilder<RedissonCacheBuilder.RedissonDataCacheBuilderImpl> remoteCacheBuilder = RedissonCacheBuilder
                .createBuilder()
                .keyConvertor(Fastjson2KeyConvertor.INSTANCE)
                .valueEncoder(JavaValueEncoder.INSTANCE)
                .valueDecoder(JavaValueDecoder.INSTANCE)
                .broadcastChannel(environment.getProperty("spring.application.name"))
                .redissonClient(redissonClient);
        remoteBuilders.put(CacheConsts.DEFAULT_AREA, remoteCacheBuilder);

        GlobalCacheConfig globalCacheConfig = new GlobalCacheConfig();
        globalCacheConfig.setLocalCacheBuilders(localBuilders);
        globalCacheConfig.setRemoteCacheBuilders(remoteBuilders);
        globalCacheConfig.setStatIntervalMinutes(15);

        return globalCacheConfig;
    }

}