package com.example.rocketmq.producer.service.impl;

import com.example.rocketmq.producer.bean.User;
import com.example.rocketmq.producer.mapper.UserMapper;
import com.example.rocketmq.producer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户Service实现类
 *
 * @author jackie
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * 用户信息缓存key前缀
     */
    private static final String KEY = "user:";

    @Autowired
    private UserMapper userMapper;

    /**
     * 查询用户信息
     *
     * @param id
     * @return 用户信息
     * @throws Exception
     */
    @Override
    public User findById(Integer id) throws Exception {
        return userMapper.findById(id);
    }


}
