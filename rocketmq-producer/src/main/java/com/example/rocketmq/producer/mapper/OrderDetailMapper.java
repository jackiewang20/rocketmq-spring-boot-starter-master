package com.example.rocketmq.producer.mapper;

import com.example.rocketmq.producer.bean.Order;
import com.example.rocketmq.producer.bean.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author jackie
 * @Title: OrderDetailMapper
 * @ProjectName rocketmq-producer-consumer
 * @Description: TODO
 * @date 2019/1/23 18:06
 */
@Component
@Mapper
public interface OrderDetailMapper {

    Integer insert(OrderDetail orderDetail) throws Exception;

    OrderDetail selectById(Long id)throws Exception;

    List<OrderDetail> selectByOrderId(Long orderId) throws Exception;

}
