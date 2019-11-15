package com.example.rocketmq.producer.mapper;

import com.example.rocketmq.producer.bean.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author jackie
 * @Title: OrderMapperTest
 * @ProjectName rocketmq-producer-consumer
 * @Description: TODO
 * @date 2019/1/24 15:12
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderMapperTest {
    @Autowired
    private OrderMapper orderMapper;


    @Transactional
    @Test
    public void insert() throws Exception {
        Order order = new Order();
        order.setCreateTime(new Date());
        order.setOrderAmount(new BigDecimal(300.00));
//        order.setOrderCode("code-001");
        order.setOrderNo("order-001");
        order.setOrderStatus(1);
        order.setShopId(1);
        order.setUserId(2);

        Integer result = orderMapper.insert(order);
        assertEquals(1, (long)result);
        System.err.println(result);
    }

    /**
     * 创建订单及订单详情，事务测试
     * @throws Exception
     */
    @Test
    public void createOrderAndDetailTx() throws Exception{



    }

    @Test
    public void selectByUserId() throws Exception {
        List<Order> list = orderMapper.selectByUserId(1);
        System.err.println(list);
    }

    @Test
    public void selectByOrderNo() throws Exception {
        Order order = orderMapper.selectByOrderNo("no-00001");
        System.err.println(order);
    }
}