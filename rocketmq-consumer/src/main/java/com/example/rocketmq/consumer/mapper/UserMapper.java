package com.example.rocketmq.consumer.mapper;

import com.example.rocketmq.consumer.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * UserMapper接口
 * @author jackie
 *
 */
@Component
@Mapper
public interface UserMapper {

	Integer insert(User user) throws Exception;
	
	User findById(Integer id) throws Exception;
	
}
