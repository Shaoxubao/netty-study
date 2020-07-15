package com.baoge.protocol_custom.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @Author shaoxubao
 * @Date 2020/7/15 15:51
 *
 * 消息枚举类型
 */

@RequiredArgsConstructor
public enum MessageType {

    /**
     * 请求
     */
    REQUEST((byte) 1),

    /**
     * 响应
     */
    RESPONSE((byte) 2),

    /**
     * PING
     */
    PING((byte) 3),

    /**
     * PONG
     */
    PONG((byte) 4),

    /**
     * NULL
     */
    NULL((byte) 5),

    ;

    @Getter
    private final Byte type;

    public static MessageType fromValue(byte value) {
        for (MessageType type : MessageType.values()) {
            if (type.getType() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException(String.format("value = %s", value));
    }

}
