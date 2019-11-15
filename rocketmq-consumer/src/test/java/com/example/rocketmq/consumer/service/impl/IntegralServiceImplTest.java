package com.example.rocketmq.consumer.service.impl;

import com.example.rocketmq.consumer.bean.Integral;
import com.example.rocketmq.consumer.service.IntegralService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * @author jackie
 * @Title: IntegralServiceImplTest
 * @ProjectName rocketmq-producer-consumer
 * @Description: TODO
 * @date 2019/1/25 18:08
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class IntegralServiceImplTest {
    @Autowired
    private IntegralService integralService;

    @Transactional
    @Test
    public void insert() throws Exception {
        Integral integral = new Integral();
        integral.setNumber(1);
        integral.setType(1);
        integral.setUserId(1);

        Integer result = integralService.insert(integral);
        assertEquals(1, (long)result);
    }

    @Test
    public void addIntegralWithUserId() throws Exception{
        Integral integral = new Integral();
        integral.setNumber(1);
        integral.setUserId(1);

        Integer result = integralService.addIntegralWithUserId(integral);
        assertEquals(1, (long)result);
    }

    @Test
    public void updateById() {
    }

    @Test
    public void selectById() {
    }
}