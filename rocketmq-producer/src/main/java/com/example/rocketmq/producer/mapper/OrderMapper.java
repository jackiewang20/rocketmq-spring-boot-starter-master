package com.example.rocketmq.producer.mapper;

import com.example.rocketmq.producer.bean.Order;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author jackie
 * @Title: OrderMapper
 * @ProjectName rocketmq-producer-consumer
 * @Description: TODO
 * @date 2019/1/23 18:06
 */
@Component
@Mapper
public interface OrderMapper {

    Integer insert(Order order) throws Exception;

    List<Order> selectByUserId(Integer userId)throws Exception;

    Order selectByOrderNo(String orderNo) throws Exception;

}
