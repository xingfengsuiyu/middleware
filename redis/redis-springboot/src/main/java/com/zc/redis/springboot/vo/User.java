package com.zc.redis.springboot.vo;

import java.io.Serializable;

/**
 * 用户
 *
 * @author zc
 * @create 2021-07-03 15:38
 **/
public class User implements Serializable {

	private static final long serialVersionUID = -6529535669925668242L;
	private String name;

	private Integer age;

	public User() {

	}
	public User(String name ,Integer age) {
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public User setName(String name) {
		this.name = name;
		return this;
	}

	public Integer getAge() {
		return age;
	}

	public User setAge(Integer age) {
		this.age = age;
		return this;
	}

	@Override
	public String toString() {
		return "User{" +
				"name='" + name + '\'' +
				", age=" + age +
				'}';
	}
}

