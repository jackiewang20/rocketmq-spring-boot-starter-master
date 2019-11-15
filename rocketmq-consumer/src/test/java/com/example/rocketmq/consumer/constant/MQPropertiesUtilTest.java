package com.example.rocketmq.consumer.constant;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author jackie
 * @Title: MQPropertiesUtilTest
 * @ProjectName rocketmq-producer-consumer
 * @Description: TODO
 * @date 2019/1/18 15:45
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MQPropertiesUtilTest {

    @Test
    public void test1(){
        String tmpTag0= "TagA||TagB";
        String tmp = tmpTag0.split("||")[0];
        System.err.println("========================");
        System.err.println(tmp);
    }

    @Test
    public void test2(){
        String tmpTag0="TagA||TagB";
        String tmp =tmpTag0.replace("||", "::");
        tmp = tmp.split("::")[0];
        System.err.println("========================");
        System.err.println(tmp);
    }

}