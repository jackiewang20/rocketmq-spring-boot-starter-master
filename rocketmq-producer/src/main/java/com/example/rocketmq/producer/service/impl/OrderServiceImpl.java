package com.example.rocketmq.producer.service.impl;

import com.example.rocketmq.producer.bean.Order;
import com.example.rocketmq.producer.bean.OrderDetail;
import com.example.rocketmq.producer.mapper.OrderDetailMapper;
import com.example.rocketmq.producer.mapper.OrderMapper;
import com.example.rocketmq.producer.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author jackie
 * @Title: OrderServiceImpl
 * @ProjectName rocketmq-producer-consumer
 * @Description: TODO
 * @date 2019/1/24 17:09
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Override
    public Integer insert(Order order) throws Exception {
        return orderMapper.insert(order);
    }

    /**
     * 事务测试：声明式配置开启事务，配合@Transactional注解;
     * 执行多条sql，如果有一个sql执行失败，回滚。
     * @param order 订单基本信息；
     * @param orderDetail 订单详细信息；
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean createOrderAndDetailTx(Order order, OrderDetail orderDetail) throws Exception {
        // 查询订单是否已经创建
        // TODO

        // 创建订单基础表
        int orderCount = orderMapper.insert(order);

        orderDetail.setOrderId(order.getId());
        // 创建订单明细表
        int orderDetailCount = orderDetailMapper.insert(orderDetail);

//        return true;
        return (orderCount + orderDetailCount)==2;
    }

    @Override
    public List<Order> selectByUserId(Integer userId) throws Exception {
        return orderMapper.selectByUserId(userId);
    }

    @Override
    public Order selectByOrderNo(String orderNo) throws Exception {
        return orderMapper.selectByOrderNo(orderNo);
    }
}
