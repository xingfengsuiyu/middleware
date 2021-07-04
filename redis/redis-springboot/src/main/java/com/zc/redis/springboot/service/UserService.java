package com.zc.redis.springboot.service;

import com.zc.redis.springboot.vo.User;

public interface UserService {

	User queryUser(String name);

	void save(User user);

	void delete(String name);
}
