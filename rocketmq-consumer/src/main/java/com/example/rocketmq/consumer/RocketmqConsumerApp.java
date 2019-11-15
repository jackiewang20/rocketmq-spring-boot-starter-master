package com.example.rocketmq.consumer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author jackie
 * @Title: RocketmqConsumerApp
 * @ProjectName rocketmq-producer-consumer
 * @Description: TODO
 * @date 2019/1/14 16:04
 */
//@MapperScan(basePackages = "com.example.rocketmq.consumer.mapper") // mapper层包扫描，如果不使用当前配置，mapper层接口需要加上注解@Mapper
@SpringBootApplication(scanBasePackages = {"com.example.rocketmq.consumer", "com.example.rocketmq.starter"})
public class RocketmqConsumerApp {
    public static void main(String[] args) {
        SpringApplication.run(RocketmqConsumerApp.class, args);
    }
}
