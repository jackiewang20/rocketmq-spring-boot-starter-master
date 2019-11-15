package com.example.rocketmq.consumer.service;

import com.example.rocketmq.consumer.bean.User;

/**
 * 用户service接口
 * @author jackie
 *
 */
public interface UserService {

	/**
	 * 查询用户信息
	 * @param id
	 * @return 用户信息
	 * @throws Exception
	 */
	User findById(Integer id) throws Exception;

}
