package com.example.rocketmq.producer.mapper;

import com.example.rocketmq.producer.bean.OrderDetail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author jackie
 * @Title: OrderDetailMapperTest
 * @ProjectName rocketmq-producer-consumer
 * @Description: TODO
 * @date 2019/1/24 16:15
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderDetailMapperTest {
    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Transactional
    @Test
    public void insert() throws Exception {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setNumber(2);
        orderDetail.setOrderId(1L);
        orderDetail.setProductId(1);
        orderDetail.setProductModeDesc("商品描述");
        orderDetail.setProductModeParams("商品类型参数");
        orderDetail.setProductName("小米充电宝");
        orderDetail.setProductPrice(new BigDecimal(99.00));
        orderDetail.setRemark("备注");
        orderDetail.setSubtotal(new BigDecimal(198.00));
        Integer result = orderDetailMapper.insert(orderDetail);
        assertEquals(1, (long)result);
    }

    @Test
    public void selectById() throws Exception {
        OrderDetail orderDetail = orderDetailMapper.selectById(1L);
        System.out.println(orderDetail);
    }

    @Test
    public void selectByOrderId() throws Exception {
        List<OrderDetail> list = orderDetailMapper.selectByOrderId(1L);
        System.out.println(list);
    }
}