package com.example.rocketmq.producer.service;

import com.example.rocketmq.producer.bean.Order;
import com.example.rocketmq.producer.bean.OrderDetail;

import java.util.List;

/**
 * @author jackie
 * @Title: OrderService
 * @ProjectName rocketmq-producer-consumer
 * @Description: TODO
 * @date 2019/1/23 18:06
 */
public interface OrderService {

    Integer insert(Order order) throws Exception;

    /**
     * 事务测试：声明式配置开启事务，配合@Transactional注解
     *
     * @param order 订单基本信息；
     * @param orderDetail 订单详细信息；
     * @return
     * @throws Exception
     */
    Boolean createOrderAndDetailTx(Order order, OrderDetail orderDetail) throws Exception;

    List<Order> selectByUserId(Integer userId) throws Exception;

    Order selectByOrderNo(String orderNo) throws Exception;

}
