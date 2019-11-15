package com.example.rocketmq.producer.service;

import com.example.rocketmq.producer.bean.Integral;

/**
 * @author jackie
 * @Title: IntegralService
 * @ProjectName rocketmq-producer-consumer
 * @Description: 积分
 * @date 2019/1/23 18:07
 */
public interface IntegralService {

    Integer insert(Integral bean) throws Exception;

    Integer updateById(Integral bean) throws Exception;

    Integral selectById(Integer productId) throws Exception;

}
