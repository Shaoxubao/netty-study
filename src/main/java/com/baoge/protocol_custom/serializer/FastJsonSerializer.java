package com.baoge.protocol_custom.serializer;

import com.alibaba.fastjson.JSON;

/**
 * @Author shaoxubao
 * @Date 2020/7/15 16:08
 */
// FastJson实现
public enum FastJsonSerializer implements Serializer {

    // 单例
    X;

    @Override
    public byte[] encode(Object target) {
        return JSON.toJSONBytes(target);
    }

    @Override
    public Object decode(byte[] bytes, Class<?> targetClass) {
        return JSON.parseObject(bytes, targetClass);
    }
}