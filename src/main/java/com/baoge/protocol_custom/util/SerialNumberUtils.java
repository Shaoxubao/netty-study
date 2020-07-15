package com.baoge.protocol_custom.util;

import java.util.UUID;

/**
 * @Author shaoxubao
 * @Date 2020/7/15 16:25
 */
public enum SerialNumberUtils {

    // 单例;
    X;

    public String generateSerialNumber() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
