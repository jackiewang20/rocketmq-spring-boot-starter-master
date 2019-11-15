package com.example.rocketmq.producer.bean;

import lombok.Data;

/**
 * 商品
 */
@Data
public class Product {
    private Integer id;

    private String product_name;

    private Double price;

    private String pictureList;

    private String specification;

    private Integer shopId;

}