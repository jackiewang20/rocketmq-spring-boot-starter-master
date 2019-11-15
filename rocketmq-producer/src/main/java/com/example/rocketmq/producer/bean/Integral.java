package com.example.rocketmq.producer.bean;

import lombok.Data;

/**
 * 积分
 */
@Data
public class Integral extends BaseBean{
    private Integer id;

    private Integer number;

    private Integer type;

    private Integer userId;


}