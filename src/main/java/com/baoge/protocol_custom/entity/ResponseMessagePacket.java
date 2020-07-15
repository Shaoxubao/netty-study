package com.baoge.protocol_custom.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author shaoxubao
 * @Date 2020/7/15 16:05
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ResponseMessagePacket extends BaseMessagePacket {

    /**
     * error code
     */
    private Long errorCode;

    /**
     * 消息描述
     */
    private String message;

    /**
     * 消息载荷
     */
    private Object payload;
}
