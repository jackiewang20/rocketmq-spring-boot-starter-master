package com.example.rocketmq.producer.component;

import com.alibaba.fastjson.JSONObject;
import com.example.rocketmq.producer.bean.Integral;
import com.example.rocketmq.producer.bean.Order;
import com.example.rocketmq.producer.bean.OrderDetail;
import com.example.rocketmq.producer.bean.OrderExt;
import com.example.rocketmq.producer.common.EnumTransCode;
import com.example.rocketmq.producer.constant.MQPropertiesUtil;
import com.example.rocketmq.producer.service.OrderService;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jackie
 * @Title: TransactionListenerImpl
 * @ProjectName rocketmq-producer-consumer
 * @Description: 实现TransactionListener接口，用于执行本地事务，
 * 执行完成后进行事务消息状态检查。
 * @date 2019/1/22 14:29
 */
@Component
public class TransactionListenerImpl implements TransactionListener {
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private DefaultMQProducer defaultMQProducer;
    @Autowired
    private OrderService orderService;

    /**
     * 临时存放本地事务状态，生产环境持久化到数据库
     * TODO
     */
    private ConcurrentHashMap<String, Integer> localTrans = new ConcurrentHashMap<>();

    /**
     * 执行本地事务：发送半消息成功时执行本地事务，根据执行结果返回对应的事务状态。
     *
     * @param message 发送到mq的事务减库存消息；
     * @param o       本地业务执行的事务参数值；
     * @return
     */
    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        try {
            // 业务逻辑：数据库操作执行本地事务，创建订单
            OrderExt orderExt = (OrderExt) o;
            Order order = orderExt.getOrder();
            OrderDetail orderDetail = orderExt.getOrderDetail();
            orderService.createOrderAndDetailTx(order, orderDetail);

            LOGGER.info("[producer]本地事务执行成功，commit事务消息。消息内容：{}", message);
            localTrans.put(message.getTransactionId(), EnumTransCode.CODE_COMMIT_MESSAGE.getCode());
        } catch (Exception e) {
            /**
             * 优化点：如果事务执行失败，代码执行到这里服务器宕机，后面的localTrans没有来得及记录本地事务执行状态,
             * 当消息回查事件触发回查的时候，怎么知晓？
             * 本地需要一个本地事务消息表，记录消息状态和本地事务执行状态
             * TODO
             */

            LOGGER.error("[producer]本地事务执行失败，rollback事务消息。消息内容：{}", message, e);
            localTrans.put(message.getTransactionId(), EnumTransCode.CODE_ROLLBACK_MESSAGE.getCode());
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }

        // 发送其它消息：加积分
        Message integralMessage = null;
        try {
            // 积分：根据userId加积分；当前用户id为1的用户剩余积分为100
            Integral integral = new Integral();
            integral.setNumber(10); // 加积分
            integral.setUserId(1);
            integral.setBizType("integralService");
            String jsonContent = JSONObject.toJSONString(integral);

            String messageBizUniqueKey = message.getKeys() + 1;
            integralMessage = new Message(MQPropertiesUtil.MQ_TOPIC_1, MQPropertiesUtil.MQ_TAG_1,
                    messageBizUniqueKey, jsonContent.getBytes());
            defaultMQProducer.send(integralMessage);
        } catch (Exception e) {
            LOGGER.error("[producer]本地事务执行成功，积分消息投递失败。消息内容：{}", integralMessage, e);
            localTrans.put(message.getTransactionId(), EnumTransCode.CODE_ROLLBACK_MESSAGE.getCode());
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }

        /**
         * 优化点：如果事务执行失败，代码执行到这里服务器宕机，后面的localTrans没有来得及记录本地事务执行状态,
         * 当消息回查事件触发回查的时候，怎么知晓？
         * 本地需要由一个本地事务消息表，记录消息状态和本地事务执行状态
         * TODO
         */

        return LocalTransactionState.COMMIT_MESSAGE;
    }

    /**
     * 检查本地事务状态：检查本地事务状态，并处理MQ检查请求，根据检查结果返回对应的事务状态。
     *
     * @param messageExt
     * @return
     */
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        /**
         * 优化点：
         * 生产环境中，消息持久化到数据库，localTrans从数据库获取待确认的事务消息发送状态。
         */
        Integer status = localTrans.get(messageExt.getTransactionId());
        LOGGER.info("[producer]本地消息检查，消息状态码：{}，消息内容：{}", status, messageExt);
        if (null != status) {
            return EnumTransCode.getContentByCode(status);
        }

        return LocalTransactionState.ROLLBACK_MESSAGE;
    }
}
