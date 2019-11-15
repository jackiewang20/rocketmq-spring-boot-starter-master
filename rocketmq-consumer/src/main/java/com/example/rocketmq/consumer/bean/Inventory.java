package com.example.rocketmq.consumer.bean;

import lombok.Data;

/**
 * 库存
 */
@Data
public class Inventory extends BaseBean {
    private Integer id;

    private Integer number;

    private String channelNo;

    private Integer productId;


}