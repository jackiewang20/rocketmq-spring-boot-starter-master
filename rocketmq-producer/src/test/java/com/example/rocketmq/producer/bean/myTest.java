package com.example.rocketmq.producer.bean;

import com.example.rocketmq.producer.common.EnumTransCode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author jackie
 * @Title: EnumTransCodeTest
 * @ProjectName rocketmq-producer-consumer
 * @Description: TODO
 * @date 2019/1/22 15:16
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class myTest {

    @Test
    public void t() {

        int j=0;
        for (int i=0;i<3;i++){
            while (true && j<5){
                j++;
                System.err.println("hello");
                if(j==5)
                    break;
            }
        }

    }
}