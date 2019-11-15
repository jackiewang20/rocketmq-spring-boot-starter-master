package com.example.rocketmq.producer.service.impl;

import com.example.rocketmq.producer.bean.OrderDetail;
import com.example.rocketmq.producer.service.OrderDetailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * @author jackie
 * @Title: OrderDetailServiceImplTest
 * @ProjectName rocketmq-producer-consumer
 * @Description: TODO
 * @date 2019/1/25 19:40
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderDetailServiceImplTest {
    @Autowired
    private OrderDetailService orderDetailService;

    @Test
    public void insert() throws Exception {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId(1L);
        orderDetail.setNumber(1);
        orderDetail.setProductId(2);// product_no值p-0002
        orderDetail.setProductModeDesc("blue,250g");
        orderDetail.setProductModeParams("tx商品类型参数");
        orderDetail.setProductName("小米");
        orderDetail.setProductPrice(new BigDecimal(1999));
        orderDetail.setRemark("备注");
        orderDetail.setSubtotal(new BigDecimal(1999));

        Integer result = orderDetailService.insert(orderDetail);
        assertEquals(1, (long)result);
    }

    @Test
    public void selectById() {
    }

    @Test
    public void selectByOrderId() {
    }
}