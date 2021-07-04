package com.zc.redis.springboot.controller;

import com.zc.redis.springboot.annotations.RedisCache;
import com.zc.redis.springboot.service.UserService;
import com.zc.redis.springboot.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;

import java.util.concurrent.Semaphore;

/**
 * @author zc
 * @create 2021-07-04 9:40
 **/
@Controller
public class UserController {
	@Autowired
	private UserService userService;

	private int i = 0;

	Semaphore semaphore = new Semaphore(30);

	/**
	 * 使用cache缓存
	 */
	@CachePut(value = "user", key = "#user.name", condition = "#result ne null")
	public User cachePut(User user) {
		userService.save(user);
		return user;
	}

	@Cacheable(value = "user", key = "#name", condition = "#result ne null")
	public User cacheable(String name) {
		try {
			// 控制访问数据库的数量
			semaphore.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("查询数据库次数：" + i++);
		return userService.queryUser(name);
	}

	@CacheEvict(value = "user", key = "#name")
	public void cacheEvict(String name) {
		userService.delete(name);
	}

	@RedisCache(value = "user", key = "#name")
	public User redisCache(String name) {
		return userService.queryUser(name);
	}
}
