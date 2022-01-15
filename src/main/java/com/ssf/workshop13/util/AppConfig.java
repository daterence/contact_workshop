package com.ssf.workshop13.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import static com.ssf.workshop13.util.Constants.BEAN_CONTACT_CACHE;
import static com.ssf.workshop13.util.Constants.ENV_REDIS_PASSWORD;

@Configuration
public class AppConfig {
    private final Logger logger = LoggerFactory.getLogger(AppConfig.class);

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value(("${spring.redis.port}"))
    private Integer redisPort;

    @Value("${spring.redis.database}")
    private Integer redisDatabase;

    private final String redisPassword;


    public AppConfig() { redisPassword = System.getenv(ENV_REDIS_PASSWORD);}

    @Bean(BEAN_CONTACT_CACHE)
    public RedisTemplate<String, String> redisTemplateFactory() {
        final RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        logger.info("redis host port >> " + redisHost + ' ' + redisPort + ' ' + redisPassword);

        redisConfig.setHostName(redisHost);
        redisConfig.setPort(redisPort);

        if (null != redisPassword) {
            redisConfig.setPassword(redisPassword);
            logger.info("Set Redis password >>> " + redisPassword);
        }
        // only for localhost
        redisConfig.setDatabase(redisDatabase);

        final JedisClientConfiguration jedisConfig = JedisClientConfiguration.builder().build();
        final JedisConnectionFactory jedisFactory = new JedisConnectionFactory(redisConfig, jedisConfig);
        jedisFactory.afterPropertiesSet();

        final RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());

        return template;
    }
}
