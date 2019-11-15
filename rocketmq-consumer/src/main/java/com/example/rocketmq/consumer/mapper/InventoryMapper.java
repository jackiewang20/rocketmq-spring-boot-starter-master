package com.example.rocketmq.consumer.mapper;

import com.example.rocketmq.consumer.bean.Inventory;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author jackie
 * @Title: InventoryMapper
 * @ProjectName rocketmq-producer-consumer
 * @Description: 库存
 * @date 2019/1/23 18:07
 */
@Component
@Mapper
public interface InventoryMapper {

    Integer insert(Inventory bean) throws Exception;

    Integer updatebyId(Inventory bean) throws Exception;

    List<Inventory> selectByProductId(Integer productId) throws Exception;

    Inventory selectById(Integer id) throws Exception;

    Integer subtractInventoryByProductId(Inventory bean) throws Exception;
}
