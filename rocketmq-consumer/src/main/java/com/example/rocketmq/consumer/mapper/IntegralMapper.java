package com.example.rocketmq.consumer.mapper;

import com.example.rocketmq.consumer.bean.Integral;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author jackie
 * @Title: IntegralMapper
 * @ProjectName rocketmq-producer-consumer
 * @Description: 积分
 * @date 2019/1/23 18:07
 */
@Component
@Mapper
public interface IntegralMapper {

    Integer insert(Integral bean) throws Exception;

    Integer updateById(Integral bean) throws Exception;

    Integral selectById(Integer productId) throws Exception;

    Integer addIntegralWithUserId(Integral bean) throws Exception;
}
