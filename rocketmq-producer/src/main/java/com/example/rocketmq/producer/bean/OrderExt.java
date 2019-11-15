package com.example.rocketmq.producer.bean;

import lombok.Data;

/**
 * @author jackie
 * @Title: OrderExt
 * @ProjectName rocketmq-producer-consumer
 * @Description: TODO
 * @date 2019/1/24 18:16
 */
@Data
public class OrderExt {
    private Order order;
    private OrderDetail orderDetail;
}
