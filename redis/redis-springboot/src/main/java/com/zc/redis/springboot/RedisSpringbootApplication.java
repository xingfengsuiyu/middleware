package com.zc.redis.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableCaching
@EnableAspectJAutoProxy
@MapperScan("com.zc.redis.springboot.dao")
public class RedisSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisSpringbootApplication.class, args);
	}

}
