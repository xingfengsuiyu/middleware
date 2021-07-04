package com.zc.redis.springboot.dao;

import com.zc.redis.springboot.vo.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
	User queryUser(String name);

	void save(User user);

	void delete(String name);
}
