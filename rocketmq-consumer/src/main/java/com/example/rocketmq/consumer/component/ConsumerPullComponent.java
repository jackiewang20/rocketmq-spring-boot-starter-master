package com.example.rocketmq.consumer.component;

import com.example.rocketmq.consumer.constant.Constants;
import com.example.rocketmq.consumer.constant.MQPropertiesUtil;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author jackie
 * @Title: ConsumerPullComponent
 * @ProjectName rocketmq-producer-consumer
 * @Description: 消费者通过PULL模式从MQ拉取消息
 * @date 2019/1/29 15:34
 */
@Component
public class ConsumerPullComponent {
    Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private DefaultMQPullConsumer defaultMQPullConsumer;


    /**
     * 消费
     */
    public void consume() {
        // 获取该主题下的所有队列
        Set<MessageQueue> mqs;
        try {
            mqs = defaultMQPullConsumer.fetchSubscribeMessageQueues(MQPropertiesUtil.MQ_TOPIC_1);
        } catch (MQClientException e) {
            LOGGER.error("PULL模式消费者连接MQ失败：", e);
            return;
        }

        // 遍历消息队列
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(mqs.size());
        for (MessageQueue mq : mqs) {
            fixedThreadPool.execute(new Runnable(){

                @Override
                public void run() {

                    LOGGER.info("[PULL]当前的消费队列:{}", mq);

                    while (true) {
                        try {
                            // 获取消费的消息偏移量
                            long offset = defaultMQPullConsumer.fetchConsumeOffset(mq, false);
                            if (offset < 0) {
                                offset = 0;
                            }
//                            System.err.println("[PULL]获取当前队列："+ mq.getQueueId()+ "，消费的消息偏移量:" +offset);
//                            LOGGER.info("[PULL]获取当前队列：{}，消费的消息偏移量：{}" ,mq.getQueueId(), offset);

                            // 获取PULL结果，成功获取后OFFSET会递增+1
                            PullResult pullResult = defaultMQPullConsumer.pullBlockIfNotFound(mq, null
                                    , offset, 32);
//                            LOGGER.info("[PULL]正在消费消息：{}", pullResult);
                            switch (pullResult.getPullStatus()) {
                                case FOUND:
                                    // 批量消费消息
                                    List<MessageExt> messageExtList = pullResult.getMsgFoundList();
                                    for (MessageExt m : messageExtList) {
                                        LOGGER.info("[pull]消费消息：{}；详细信息：{}"
                                                ,new String(m.getBody(), Constants.DEFAULT_ENCODING), m);
                                    }
                                    break;
                                case NO_MATCHED_MSG:
                                    break;
                                case NO_NEW_MSG:
                                    // TODO 生成环境休眠时间可以设置10ms
                                    Thread.sleep(1000);
                                    break;
                                case OFFSET_ILLEGAL:
                                    break;
                                default:
                                    break;
                            }

                            // 更新消费的消息偏移量
                            defaultMQPullConsumer.updateConsumeOffset(mq, pullResult.getNextBeginOffset());

//                            LOGGER.info("[PULL]更新当前队列：{}，消费的消息偏移量：{}" ,mq.getQueueId(), pullResult.getNextBeginOffset());
//                            System.err.println("[PULL]更新当前队列："+ mq.getQueueId()+ "，消费的消息偏移量:" +pullResult.getNextBeginOffset());
                        } catch (Exception e) {
                            LOGGER.error("[PULL]Consumption failed :", e);
                        }
                    }

                }
            });

        }

    }



}
