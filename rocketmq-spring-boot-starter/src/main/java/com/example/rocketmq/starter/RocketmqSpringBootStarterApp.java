package com.example.rocketmq.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author jackie
 * @Title: RocketmqSpringBootStarter
 * @ProjectName rocketmq-producer-consumer
 * @Description: TODO
 * @date 2019/1/14 15:55
 */
@SpringBootApplication
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class RocketmqSpringBootStarterApp {

    public static void main(String[] args) {
        SpringApplication.run(RocketmqSpringBootStarterApp.class,args);
    }

}
