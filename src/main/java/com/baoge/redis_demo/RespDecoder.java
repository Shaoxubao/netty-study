package com.baoge.redis_demo;

import io.netty.buffer.ByteBuf;

/**
 * @Author shaoxubao
 * @Date 2020/7/16 14:43
 */

public interface RespDecoder<V>{

    V decode(ByteBuf buffer);

}
