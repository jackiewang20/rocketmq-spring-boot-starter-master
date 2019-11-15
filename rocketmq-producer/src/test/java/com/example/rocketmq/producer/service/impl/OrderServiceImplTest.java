package com.example.rocketmq.producer.service.impl;

import com.example.rocketmq.producer.bean.Order;
import com.example.rocketmq.producer.bean.OrderDetail;
import com.example.rocketmq.producer.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * @author jackie
 * @Title: OrderServiceImplTest
 * @ProjectName rocketmq-producer-consumer
 * @Description: TODO
 * @date 2019/1/24 17:16
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderServiceImplTest {
    @Autowired
    private OrderService orderService;

    @Test
    public void insert() throws Exception {
        // 订单基础
        Order order = new Order();
        order.setOrderNo("orderNo-gen-004");
        order.setCreateTime(new Date());
        order.setOrderAmount(new BigDecimal(1999));
        order.setOrderStatus(1);
        order.setShopId(1);
        order.setUserId(1);
        Integer result = orderService.insert(order);
//        assertEquals(1, (long)result);

        System.err.println("执行结果："+result);
        System.err.println("递增id："+order.getId());
    }


    /**
     * 事务测试：声明式配置开启事务，配合@Transactional注解
     */
//    @Transactional
    @Test
    public void createOrderAndDetailTx() throws Exception {
        Long orderId = 1L;
        // 订单基础表
        Order order = new Order();
        order.setCreateTime(new Date());
        order.setOrderAmount(new BigDecimal(101.00));
        order.setOrderNo("no-test");
        order.setOrderStatus(1);
        order.setShopId(1);
        order.setUserId(1);
        // 订单详情表
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setNumber(1);
        orderDetail.setOrderId(orderId);
        orderDetail.setProductId(2);
        orderDetail.setProductModeDesc("tx商品描述");
        orderDetail.setProductModeParams("tx商品类型参数");
        orderDetail.setProductName("tx充电宝");
        orderDetail.setProductPrice(new BigDecimal(101));
        orderDetail.setRemark("备注");
        orderDetail.setSubtotal(new BigDecimal(101));

        Boolean result = orderService.createOrderAndDetailTx(order, orderDetail);
        assertEquals(true, result);
    }

    @Test
    public void selectByUserId() {
    }

    @Test
    public void selectByOrderNo() {
    }
}