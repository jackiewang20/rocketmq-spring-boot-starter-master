package com.example.rocketmq.producer.common;

import org.apache.rocketmq.client.producer.LocalTransactionState;

/**
 * @author jackie
 * @Title: EnumTransCode
 * @ProjectName rocketmq-producer-consumer
 * @Description: 事务状态码
 * @date 2019/1/22 14:40
 */
public enum EnumTransCode {

    /** 不知道 */
    CODE_UNKNOW(0, LocalTransactionState.UNKNOW),
    /** 提交消息 */
    CODE_COMMIT_MESSAGE(1, LocalTransactionState.COMMIT_MESSAGE),
    /** 回滚消息 */
    CODE_ROLLBACK_MESSAGE(2, LocalTransactionState.ROLLBACK_MESSAGE);

    public Integer code;
    public LocalTransactionState localTransactionState;

    EnumTransCode(Integer code, LocalTransactionState localTransactionState) {
        this.code = code;
        this.localTransactionState = localTransactionState;
    }

    private static EnumTransCode getTypeByCode(Integer code) {
        EnumTransCode defaultType = EnumTransCode.CODE_UNKNOW;
        for (EnumTransCode c: EnumTransCode.values()) {
            if(c.code.equals(code)) {
                return c;
            }
        }
        return defaultType;
    }

    public static LocalTransactionState getContentByCode(Integer code) {
        return getTypeByCode(code).localTransactionState;
    }

    public Integer getCode() {
        return code;
    }

    public LocalTransactionState getLocalTransactionState() {
        return localTransactionState;
    }
}
