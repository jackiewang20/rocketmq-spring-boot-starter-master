package com.example.rocketmq.producer.mapper;

import com.example.rocketmq.producer.bean.Inventory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author jackie
 * @Title: InventoryMapperTest
 * @ProjectName rocketmq-producer-consumer
 * @Description: TODO
 * @date 2019/1/24 12:48
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class InventoryMapperTest {
    @Autowired
    private InventoryMapper inventoryMapper;

    @Transactional
    @Test
    public void insert() throws Exception {
        Inventory inventory = new Inventory();
        inventory.setNumber(10);
        inventory.setChannelNo("no002");
        inventory.setProductId(2);
        Integer result = inventoryMapper.insert(inventory);
        assertEquals(1, (long)result);
    }

    @Transactional
    @Test
    public void updatebyId() throws Exception {
        Inventory inventory = new Inventory();
        inventory.setId(4);
        inventory.setNumber(100);
        inventory.setChannelNo("no0022");
        inventory.setProductId(22);
        Integer result = inventoryMapper.updatebyId(inventory);
        assertEquals(1, (long)result);
    }

    @Test
    public void selectByProductId() throws Exception {
        List<Inventory> list = inventoryMapper.selectByProductId(1);
        System.out.println(list);
    }

    @Test
    public void selectById() throws Exception {
        Inventory inventory = inventoryMapper.selectById(1);
        System.out.println(inventory);
    }

}