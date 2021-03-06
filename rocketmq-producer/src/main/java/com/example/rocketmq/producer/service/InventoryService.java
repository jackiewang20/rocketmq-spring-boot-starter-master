package com.example.rocketmq.producer.service;

import com.example.rocketmq.producer.bean.Inventory;

import java.util.List;

/**
 * @author jackie
 * @Title: InventoryService
 * @ProjectName rocketmq-producer-consumer
 * @Description: 库存
 * @date 2019/1/23 18:07
 */
public interface InventoryService {

    Integer insert(Inventory bean) throws Exception;

    Integer updatebyId(Inventory bean) throws Exception;

    List<Inventory> selectByProductId(Integer productId) throws Exception;

    Inventory selectById(Integer id) throws Exception;

}
