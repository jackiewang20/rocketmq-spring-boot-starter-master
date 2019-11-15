package com.example.rocketmq.starter.configuration;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

import static com.example.rocketmq.starter.configuration.RocketmqProperties.PREFIX;

/**
 * @author jackie
 * @Title: MQProducerAutoConfiguration
 * @ProjectName rocketmq-producer-consumer
 * @Description: MQ生产者自动配置
 * @date 2019/1/15 10:20
 */
@Configuration
@EnableConfigurationProperties(RocketmqProperties.class) // 激活配置属性
@ConditionalOnProperty(prefix = PREFIX, value = "namesrvAddr") // 条件加载配置属性，如果满足条件，实例化当前类
public class MQProducerAutoConfiguration {
    Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private RocketmqProperties properties;


    /**
     * 初始化向rocketmq，发送普通消息的生产者
     */
    @Bean
    @ConditionalOnProperty(prefix = PREFIX, value = "producer.instanceName")
    public DefaultMQProducer defaultMQProducer() throws MQClientException {
        /**
         * 一个应用创建一个Producer，由应用来维护此对象，可以设置为全局对象或者单例。
         * 注意：ProducerGroupName需要由应用来保证唯一；
         * ProducerGroup这个概念发送普通的消息时，作用不大，但是发送分布式事务消息时，比较关键，
         * 因为服务器会回查这个Group下的任意一个Producer
         */
        DefaultMQProducer producer = new DefaultMQProducer(properties.getProducer().getProducerGroupName());
        producer.setNamesrvAddr(properties.getNamesrvAddr());
        producer.setInstanceName(properties.getProducer().getInstanceName());
        producer.setVipChannelEnabled(properties.getProducer().getVipChannelEnabled());

        /**
         * Producer对象在使用之前必须要调用start初始化，初始化一次即可。
         * 注意：切记不可以在每次发送消息时，都调用start方法
         */
        producer.start();
        LOGGER.info("RocketMQ defaultProducer Started.");
        return producer;
    }

    /**
     * 初始化向rocketmq，发送事务消息的生产者
     */
    @Bean
    @ConditionalOnProperty(prefix = PREFIX, value = "producer.tranInstanceName")
    public TransactionMQProducer transactionMQProducer() throws MQClientException {
        /**
         * 一个应用创建一个Producer，由应用来维护此对象，可以设置为全局对象或者单例。
         * 注意：ProducerGroupName需要由应用来保证唯一；
         * ProducerGroup这个概念发送普通的消息时，作用不大，但是发送分布式事务消息时，比较关键，
         * 因为服务器会回查这个Group下的任意一个Producer。
         */
        TransactionMQProducer producer = new TransactionMQProducer(properties.getProducer().getTranProducerGroupName());
        producer.setNamesrvAddr(properties.getNamesrvAddr());
        producer.setInstanceName(properties.getProducer().getTranInstanceName());

        // 消息重发线程
        ExecutorService executorService = new ThreadPoolExecutor(properties.getProducer().getThreadPoolMinSize(), properties.getProducer().getThreadPoolMaxSize(), properties.getProducer().getKeepAliveTime()
                , TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(properties.getProducer().getBlockingQueueCapacity()), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("client-transaction-msg-check-thread");
                return thread;
            }
        });

        producer.setExecutorService(executorService);
        producer.setVipChannelEnabled(properties.getProducer().getVipChannelEnabled());

        /**
         * Producer对象在使用之前必须要调用start初始化，初始化一次即可。
         * 注意：切记不可以在每次发送消息时，都调用start方法。
         */
        producer.start();
        LOGGER.info("RocketMQ TransactionMQProducer Started.");
        return producer;
    }


}
