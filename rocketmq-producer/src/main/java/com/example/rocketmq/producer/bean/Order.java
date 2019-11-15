package com.example.rocketmq.producer.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Order {
    private Long id;
    private String orderCode;
    private String orderNo;
    private Integer shopId;
    private Integer orderStatus;
    private BigDecimal orderAmount;
    private Date createTime;
    private Integer userId;

}