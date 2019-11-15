package com.example.rocketmq.producer.service.impl;

import com.example.rocketmq.producer.bean.Integral;
import com.example.rocketmq.producer.mapper.IntegralMapper;
import com.example.rocketmq.producer.service.IntegralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jackie
 * @Title: IntegralServiceImpl
 * @ProjectName rocketmq-producer-consumer
 * @Description: 积分Service
 * @date 2019/1/25 18:01
 */
@Service
public class IntegralServiceImpl implements IntegralService {
    @Autowired
    private IntegralMapper integralMapper;

    @Override
    public Integer insert(Integral bean) throws Exception {
        return integralMapper.insert(bean);
    }

    @Override
    public Integer updateById(Integral bean) throws Exception {
        return integralMapper.updateById(bean);
    }

    @Override
    public Integral selectById(Integer productId) throws Exception {
        return integralMapper.selectById(productId);
    }
}
