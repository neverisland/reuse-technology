package cn.yang.authentication.authorization.config;

import cn.yang.authentication.authorization.enums.CacheSpaceEnum;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;
import java.util.Map;

/**
 * 使用spring cache 配置
 *
 * @author : 未见清海
 */
@Configuration
@EnableCaching
public class AuthCachingConfig {

//    @Value("#{${secondAuthLoginIdCodeValidityPeriod:5}}")
//    private Long secondAuthLoginIdCodeValidityPeriod;

    @Bean
    public CacheProperties cacheProperties() {
        return new CacheProperties();
    }

    @Primary
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory, CacheProperties cacheProperties) {
        // 设置默认的缓存配置，包括默认的过期时间
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig();
        if (cacheProperties != null && cacheProperties.getRedis() != null) {
            if (cacheProperties.getRedis().getTimeToLive() != null) {
                defaultCacheConfig = defaultCacheConfig.entryTtl(cacheProperties.getRedis().getTimeToLive());
            }
            if (cacheProperties.getRedis().isCacheNullValues()) {
                defaultCacheConfig = defaultCacheConfig.disableCachingNullValues();
            }
        }

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(defaultCacheConfig) // 应用默认配置
                .withInitialCacheConfigurations(Map.ofEntries(
                        Map.entry(CacheSpaceEnum.SLIDING_VERIFICATION_CODE.getMark(), RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(3)))
                ))
                .build();
    }
}
