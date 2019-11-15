package com.example.rocketmq.producer.bean;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单明细
 */
@Data
public class OrderDetail {
    private Long id;

    private Long orderId;
    private Integer productId;
    private String productName;

    private BigDecimal productPrice;

    private String productModeDesc;

    private String productModeParams;

    private Integer number;

    private BigDecimal subtotal;

    private String remark;

}