package com.baoge.protocol_custom.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author shaoxubao
 * @Date 2020/7/15 15:53
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class RequestMessagePacket extends BaseMessagePacket {

    /**
     * 接口全类名
     */
    private String interfaceName;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 方法参数签名
     */
    private String[] methodArgumentSignatures;

    /**
     * 方法参数
     */
    private Object[] methodArguments;
}
