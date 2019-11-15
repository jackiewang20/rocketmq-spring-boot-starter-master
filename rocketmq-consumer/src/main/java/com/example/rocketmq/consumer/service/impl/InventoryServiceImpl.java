package com.example.rocketmq.consumer.service.impl;

import com.example.rocketmq.consumer.bean.Inventory;
import com.example.rocketmq.consumer.mapper.InventoryMapper;
import com.example.rocketmq.consumer.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jackie
 * @Title: InventoryServiceImpl
 * @ProjectName rocketmq-producer-consumer
 * @Description: 库存服务
 * @date 2019/1/25 18:05
 */
@Service
public class InventoryServiceImpl implements InventoryService {
    @Autowired
    private InventoryMapper inventoryMapper;

    @Override
    public Integer insert(Inventory bean) throws Exception {
        return inventoryMapper.insert(bean);
    }

    @Override
    public Integer updatebyId(Inventory bean) throws Exception {
        return inventoryMapper.updatebyId(bean);
    }

    @Override
    public Integer subtractInventoryByProductId(Inventory bean) throws Exception {
        return inventoryMapper.subtractInventoryByProductId(bean);
    }

    @Override
    public List<Inventory> selectByProductId(Integer productId) throws Exception {
        return inventoryMapper.selectByProductId(productId);
    }

    @Override
    public Inventory selectById(Integer id) throws Exception {
        return inventoryMapper.selectById(id);
    }
}
