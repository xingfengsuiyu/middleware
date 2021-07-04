package com.zc.redis.springboot.config.standalone.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

/**
 * @author zc
 * @create 2021-06-30 22:13
 **/
@Configuration
@Profile("jedis")
public class RedisConfiguration {
	@Bean
	public JedisConnectionFactory redisConnectionFactory() {

		RedisStandaloneConfiguration config = new RedisStandaloneConfiguration("server", 6379);
		return new JedisConnectionFactory(config);
	}
}
