package com.zc.redis.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * @author zc
 * @create 2021-06-30 21:58
 **/
@RestController
public class HelloController {

	@Autowired
	private RedisTemplate redisTemplate;

	@RequestMapping("/hello/{id}")
	public String hello(@PathVariable("id") String id) {
		boolean b = redisTemplate.hasKey(id);
		if (b) {
			return (String) redisTemplate.opsForValue().get(id);
		} else {
			redisTemplate.opsForValue().set(id, id + " " + System.currentTimeMillis());
		}
		return "query is null";
	}

	@RequestMapping("while")
	public void whileRedis() throws InterruptedException {
		// 循环测试redis哨兵挂机是否影响
		for (int i = 0; i < 100; i++) {
			redisTemplate.opsForValue().increment("ds");
			System.out.println("ds value is " + i);
			Thread.sleep(1000L);
		}
	}

	public void list() {
		redisTemplate.opsForList().leftPush("list", "1");
		redisTemplate.opsForList().leftPush("list", "2");

		List list2 = (List) redisTemplate.boundListOps("list").range(0, 10);
		if (list2 != null) {
			list2.forEach(System.out::println);
		}
	}

	public void set() {
		redisTemplate.opsForSet().add("set", 1);
		redisTemplate.opsForSet().add("set", 2);

		Set set = (Set) redisTemplate.opsForSet().members("set");
		if (set != null) {
			set.forEach(System.out::println);
		}
	}
}
