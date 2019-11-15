package com.example.rocketmq.consumer.component;

import com.example.rocketmq.consumer.constant.MQPropertiesUtil;
import com.example.rocketmq.starter.configuration.RocketmqProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author jackie
 * @Title: SpringInitUtil
 * @ProjectName rocketmq-producer-consumer
 * @Description: 初始化
 * @date 2019/1/18 12:58
 */
//@Configuration
//@EnableConfigurationProperties(RocketmqProperties.class)
@Component
public class SpringInitUtil {
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private ConsumerPullComponent consumerPullComponent;

    @Autowired
    private MQPropertiesUtil mqPropertiesUtil;
    @Autowired
    private RocketmqProperties rocketmqProperties;

    /**
     * @PostConstruct注解使用后，该方法在程序初始化方法之前运行。只运行一次。
     */
    @PostConstruct
    public void init() {
        mqPropertiesUtil.init();
//        mqPropertiesUtil.test();

        // PULL模式初始化消费者
        consumerPullComponent.consume();

    }

    /**
     * @PreDestroy注解使用后，该方法在程序销毁之前运行。只运行一次。
     */
    @PreDestroy
    public void destroy() {

    }

}
