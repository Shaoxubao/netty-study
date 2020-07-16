package com.baoge.redis_demo;

import io.netty.buffer.ByteBuf;

/**
 * @Author shaoxubao
 * @Date 2020/7/16 14:48
 */
public interface RespCodec {

    RespCodec X = DefaultRespCodec.X;

    <IN, OUT> OUT decode(ByteBuf buffer);

    <IN, OUT> ByteBuf encode(IN in);

}
