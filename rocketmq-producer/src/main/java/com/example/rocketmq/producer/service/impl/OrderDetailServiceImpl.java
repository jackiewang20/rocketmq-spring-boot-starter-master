package com.example.rocketmq.producer.service.impl;

import com.example.rocketmq.producer.bean.OrderDetail;
import com.example.rocketmq.producer.mapper.OrderDetailMapper;
import com.example.rocketmq.producer.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jackie
 * @Title: OrderDetailServiceImpl
 * @ProjectName rocketmq-producer-consumer
 * @Description: TODO
 * @date 2019/1/24 17:10
 */
@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Override
    public Integer insert(OrderDetail orderDetail) throws Exception {
        return orderDetailMapper.insert(orderDetail);
    }

    @Override
    public OrderDetail selectById(Long id) throws Exception {
        return orderDetailMapper.selectById(id);
    }

    @Override
    public List<OrderDetail> selectByOrderId(Long orderId) throws Exception {
        return orderDetailMapper.selectByOrderId(orderId);
    }
}
