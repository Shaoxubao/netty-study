package com.baoge.redis_demo;

import io.netty.buffer.ByteBuf;

/**
 * @Author shaoxubao
 * @Date 2020/7/16 14:53
 */
public class DefaultRespDecoder implements RespDecoder {

    @Override
    public Object decode(ByteBuf buffer) {
        throw new IllegalStateException("decoder");
    }
}
