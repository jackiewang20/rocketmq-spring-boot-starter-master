package com.example.rocketmq.producer.component;

import com.example.rocketmq.producer.constant.MQPropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
@Component
public class SpringInitUtil {
    @Autowired
    private MQPropertiesUtil mqPropertiesUtil;

    /**
     * @PostConstruct注解使用后，该方法在程序初始化方法之前运行。只运行一次。
     */
    @PostConstruct
    public void init(){
        mqPropertiesUtil.init();
//        mqPropertiesUtil.test();
    }

    /**
     * @PreDestroy注解使用后，该方法在程序销毁之前运行。只运行一次。
     */
    @PreDestroy
    public void destroy(){

    }

}
