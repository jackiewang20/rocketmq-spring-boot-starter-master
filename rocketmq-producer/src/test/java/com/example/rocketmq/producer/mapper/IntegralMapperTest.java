package com.example.rocketmq.producer.mapper;

import com.example.rocketmq.producer.bean.Integral;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * @author jackie
 * @Title: IntegralMapperTest
 * @ProjectName rocketmq-producer-consumer
 * @Description: TODO
 * @date 2019/1/23 18:44
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IntegralMapperTest {
    @Autowired
    private IntegralMapper integralMapper;

    @Transactional
    @Test
    public void insert() throws Exception {
        Integral integral = new Integral();
        integral.setNumber(100);
        integral.setType(1);
        integral.setUserId(2);

        int count = integralMapper.insert(integral);
        Assert.assertEquals(1, count);
    }

    @Transactional
    @Test
    public void updateById() throws Exception {
        Integral integral = new Integral();
        integral.setId(2);
//        integral.setNumber(1000);
        integral.setType(12);
        integral.setUserId(32);

        int result = integralMapper.updateById(integral);
        assertEquals(1, result);
    }

    @Test
    public void selectById() throws Exception {
        Integral integral = integralMapper.selectById(1);
        System.err.println(integral);
    }
}