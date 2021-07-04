package com.zc.redis.springboot.controller;

import com.zc.redis.springboot.vo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author zc
 * @create 2021-07-04 9:41
 **/
@RunWith(SpringRunner.class)
@ActiveProfiles("sentinel") // 设置profile
@SpringBootTest
public class UserControllerTest {
	@Autowired
	private UserController userController;

	@Test
	public void cachePut() {
		User user = new User();
		user.setName("cb");
		user.setAge(28);
		userController.cachePut(user);
	}

	@Test
	public void cacheEvict() {
		userController.cacheEvict("cb");
	}

	@Test
	public void cacheable() {
		User user = userController.cacheable("cb");
		System.out.println(user);
	}

	/**
	 * 模拟并发直接怼到数据库
	 */
	@Test
	public void concurrent() {
		CyclicBarrier cyclicBarrier = new CyclicBarrier(1000);
		for (int i = 0; i < 1000; i++) {
			new Thread(() -> {
				try {
					cyclicBarrier.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					e.printStackTrace();
				}
				// 查询不存在的数据
				User user = userController.cacheable("mmm");
				System.out.println("查询的用户" + user);
			}).start();
		}
	}

	@Test
	public void redisCache() {
		userController.redisCache("zc");
	}
}
