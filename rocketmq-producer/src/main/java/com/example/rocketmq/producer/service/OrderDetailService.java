package com.example.rocketmq.producer.service;

import com.example.rocketmq.producer.bean.OrderDetail;

import java.util.List;

/**
 * @author jackie
 * @Title: OrderDetailService
 * @ProjectName rocketmq-producer-consumer
 * @Description: TODO
 * @date 2019/1/23 18:06
 */
public interface OrderDetailService {

    Integer insert(OrderDetail orderDetail) throws Exception;

    OrderDetail selectById(Long id) throws Exception;

    List<OrderDetail> selectByOrderId(Long orderId) throws Exception;

}
