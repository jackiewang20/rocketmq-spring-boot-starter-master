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
 * @Description: 消费者通过PULL模式从MQ拉取消息，消息消费后的偏移量保存到内存中。
 * @date 2019/1/29 15:34
 */
@Component
public class ConsumerPullMemoryComponent {
    Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private DefaultMQPullConsumer defaultMQPullConsumer;

    /**
     * 保存消费消息的偏移量，生产环境要更新到MQ中
     * TODO
     */
    private static final Map<MessageQueue, Long> OFFSET_TABLE = new HashMap<MessageQueue, Long>();

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
                            PullResult pullResult = defaultMQPullConsumer.pullBlockIfNotFound(mq, null, getMessageQueueOffset(mq), 32);
//                            LOGGER.info("[PULL]正在消费消息：{}", pullResult);
                            putMessageQueueOffset(mq, pullResult.getNextBeginOffset());
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
                                    Thread.sleep(1000);
                                    break;
                                case OFFSET_ILLEGAL:
                                    break;
                                default:
                                    break;
                            }
                        } catch (Exception e) {
                            LOGGER.error("[PULL]Consumption failed :", e);
                        }
                    }

                }
            });

        }

    }

    /**
     * 获取消费的消息偏移量
     *
     * @param mq
     * @return
     */
    private static long getMessageQueueOffset(MessageQueue mq) {
        Long offset = OFFSET_TABLE.get(mq);
        if (offset != null) {
            return offset;
        }
        return 0;
    }

    /**
     * 保存消费的消息偏移量
     *
     * @param mq
     * @param offset
     */
    private static void putMessageQueueOffset(MessageQueue mq, Long offset) {
        OFFSET_TABLE.put(mq, offset);
    }

}
