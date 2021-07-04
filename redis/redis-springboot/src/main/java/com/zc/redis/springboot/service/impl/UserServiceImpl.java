package com.zc.redis.springboot.service.impl;

import com.zc.redis.springboot.dao.UserDao;
import com.zc.redis.springboot.service.UserService;
import com.zc.redis.springboot.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zc
 * @create 2021-07-04 9:38
 **/
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;

	@Override
	public User queryUser(String name) {
		return userDao.queryUser(name);
	}

	@Override
	public void save(User user) {
		userDao.save(user);
	}

	@Override
	public void delete(String name) {
		userDao.delete(name);
	}
}
