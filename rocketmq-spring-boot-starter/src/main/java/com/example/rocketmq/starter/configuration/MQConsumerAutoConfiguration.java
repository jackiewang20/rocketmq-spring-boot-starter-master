package com.example.rocketmq.starter.configuration;

import com.example.rocketmq.starter.bean.ConsumerConfig;
import com.example.rocketmq.starter.event.RocketmqEvent;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

import static com.example.rocketmq.starter.configuration.RocketmqProperties.PREFIX;

/**
 * @author jackie
 * @Title: MQConsumerAutoConfiguration
 * @ProjectName rocketmq-producer-consumer
 * @Description: MQ消费者自动配置
 * @date 2019/1/15 10:20
 */
@Configuration
@EnableConfigurationProperties(RocketmqProperties.class) // 激活配置属性
@ConditionalOnProperty(prefix = PREFIX, value = "namesrvAddr") // 条件加载配置属性，如果满足条件，实例化当前类
public class MQConsumerAutoConfiguration {
    Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private RocketmqProperties properties;

    @Autowired
    private ApplicationEventPublisher publisher;

    /**
     * 初始化向rocketmq，基于拉取模式的消费者
     */
    @Bean
    @ConditionalOnProperty(prefix = PREFIX, value = "consumer.consumerPullGroupName")
    public DefaultMQPullConsumer defaultMQPullConsumer() throws MQClientException {
        /**
         * 一个应用创建一个Consumer，由应用来维护此对象，可以设置为全局对象或者单例。
         * 注意：ConsumerGroupName需要由应用来保证唯一
         */
        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer(properties.getConsumer().getConsumerPullGroupName());
        consumer.setNamesrvAddr(properties.getNamesrvAddr());
        consumer.setInstanceName(properties.getConsumer().getInstanceName());
        consumer.setBrokerSuspendMaxTimeMillis(properties.getConsumer().getBrokerSuspendMaxTimeMillis());
        consumer.setConsumerPullTimeoutMillis(properties.getConsumer().getConsumerPullTimeoutMillis());
        consumer.setConsumerTimeoutMillisWhenSuspend(properties.getConsumer().getConsumerTimeoutMillisWhenSuspend());
        if("BROADCASTING".equals(properties.getConsumer().getMessageModel())) {
            consumer.setMessageModel(MessageModel.BROADCASTING);
        }else{
            consumer.setMessageModel(MessageModel.CLUSTERING);
        }
        // 注册topic集合，支持pull模式
//        consumer.setRegisterTopics(properties.getConsumer().getRegisterTopics());
        consumer.start();
        return consumer;
    }

    /**
     * 初始化rocketmq，主动推送模式的消息监听方式的消费者
     */
    @Bean
    @ConditionalOnProperty(prefix = PREFIX, value = "consumer.consumerPushGroupName")
    public DefaultMQPushConsumer defaultMQPushConsumer() throws MQClientException {
        /**
         * 一个应用创建一个Consumer，由应用来维护此对象，可以设置为全局对象或者单例。
         * 注意：ConsumerGroupName需要由应用来保证唯一
         */
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(properties.getConsumer().getConsumerPushGroupName());
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.setNamesrvAddr(properties.getNamesrvAddr());
        consumer.setInstanceName(properties.getConsumer().getInstanceName());
        consumer.setConsumeMessageBatchMaxSize(properties.getConsumer().getConsumeMessageBatchMaxSize());
        consumer.setConsumeThreadMin(properties.getConsumer().getConsumeThreadMin());
        consumer.setConsumeThreadMax(properties.getConsumer().getConsumeThreadMax());
        if("BROADCASTING".equals(properties.getConsumer().getMessageModel())) {
            consumer.setMessageModel(MessageModel.BROADCASTING);
        }else{
            consumer.setMessageModel(MessageModel.CLUSTERING);
        }

        // 订阅TOPICS和TAGS
        List<String> subscribeList = properties.getTopicAndTags();
        for (String subscribe : subscribeList){
            /**
             * 根据源码分析subscribe方法的两个参数是map类型的key value，topic参数是map类型中的key，可以多次循环put;
             *
             * 订阅TOPIC和TAG示例：
             * consumer.subscribe("TopicTest", "*");
             * 或
             * consumer.subscribe("TopicTest", "TagA||TagB");
             * 其中*代表订阅Topic下所有TAG下的所有消息，如果客户端需要订阅多条TAG，使用"TagA||TagB"表示，中间用||分隔，
             * 将多个TAG进行订阅。
             * 注意：客户端使用的时候需要监听某个TOPIC下的某个TAG，不能使用监听多个TAG。
             */
            consumer.subscribe(subscribe.split(":")[0], subscribe.split(":")[1]);
        }

        /**
         * 消费者注册监听事件到RocketMQ中：当MQ push消息到消费者，消费者事件会触发并处理消息。
         * 事件实现方式：
         *     @EventListener(condition = "#event.topic=='testTopic' && #event.tag=='TagA'")
         *     public void addPoint(RocketmqEvent event){。。。}
         */
        consumer.registerMessageListener((List<MessageExt> msgs, ConsumeConcurrentlyContext context)->{
            MessageExt msg = msgs.get(0);
            try {
                // 默认msgs里只有一条消息，可以通过设置consumeMessageBatchMaxSize参数来批量接收消息
                LOGGER.info(Thread.currentThread().getName() + "Receive new messages:" + msgs.size());
                // 发布消息到达的事件，以便分发到每个tag的监听方法
                this.publisher.publishEvent(new RocketmqEvent(msg,consumer));
                LOGGER.info("[PUSH消息事件处理]接收到消息，并发布事件成功。");
//                if(true){
//                    throw new RuntimeException("[PUSH消息事件处理]抛异常测试，支持集群模式。");
//                }
            } catch(Exception e){
                // 客户端消费消息失败，会调用consumer.sendMessageBack()重复尝试消费3次
                if(msg.getReconsumeTimes() <3) {
                    LOGGER.error("[PUSH消息事件处理]客户端重试消费3-{}次，消息详细信息：{}", msg.getReconsumeTimes()+1, msg);
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                } else {
                    LOGGER.error("[PUSH消息事件处理]客户端多次尝试消费失败；消息内容：{}，异常信息{}：", msg, e);
                    // TODO 将当前消息持久化，后续重新发送消息并消费

                }
            }

            //如果没有return success，consumer会重复消费此信息，直到success。
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        // 启动消费者
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    /**
                     * 延迟2秒再启动：
                     * 主要是等待spring事件监听相关程序初始化完成，否则，可能会出现对RocketMQ的消息进行消费
                     * 后立即发布消息到达的事件，然而此事件的监听程序还未初始化，从而造成消息的丢失。
                     *
                     * Consumer对象在使用之前必须要调用start初始化，初始化一次即可。
                     */
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    LOGGER.error("当前线程休眠执行失败：", e);
                }

                try{
                    consumer.start();
                    LOGGER.info("RocketMQ pushConsumer started.");
                } catch(Exception e) {
                    LOGGER.error("DefaultMQPushConsumer启动失败：", e);
                }
            }
        }).start();

        return consumer;
    }


}
