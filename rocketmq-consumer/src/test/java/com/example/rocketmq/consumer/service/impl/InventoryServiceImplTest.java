package com.example.rocketmq.consumer.service.impl;

import com.example.rocketmq.consumer.bean.Inventory;
import com.example.rocketmq.consumer.mapper.InventoryMapper;
import com.example.rocketmq.consumer.service.InventoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author jackie
 * @Title: InventoryServiceImplTest
 * @ProjectName rocketmq-producer-consumer
 * @Description: TODO
 * @date 2019/1/25 18:40
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class InventoryServiceImplTest {
    @Autowired
    private InventoryService inventoryService;

    @Test
    public void insert() {
    }

    @Test
    public void updatebyId() {
    }

    /**
     * 减库存
     */
    @Test
    public void subtractInventoryByProductId() throws Exception {
        Inventory inventory = new Inventory();
        inventory.setNumber(100);
        inventory.setProductId(2);

        Integer result = inventoryService.subtractInventoryByProductId(inventory);
        assertEquals(1, (long)result);
    }

    @Test
    public void selectByProductId() {
    }

    @Test
    public void selectById() {
    }
}