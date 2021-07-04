package com.zc.redis.springboot.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zc
 * @create 2021-07-03 16:04
 **/
@RunWith(SpringRunner.class)
@ActiveProfiles("sentinel") // 设置profile
@SpringBootTest
public class HelloControllerTest {

	@Autowired
	private HelloController helloController;

	@Test
	public void list() {
		helloController.list();
	}

	@Test
	public void set() {
		helloController.set();
	}
}
