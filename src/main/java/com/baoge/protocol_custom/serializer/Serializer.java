package com.baoge.protocol_custom.serializer;

/**
 * @Author shaoxubao
 * @Date 2020/7/15 16:06
 */
public interface Serializer {

    byte[] encode(Object target);

    Object decode(byte[] bytes, Class<?> targetClass);
}
