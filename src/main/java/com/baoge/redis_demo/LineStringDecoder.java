package com.baoge.redis_demo;

import com.baoge.redis_demo.utils.CodecUtils;
import io.netty.buffer.ByteBuf;

/**
 * @Author shaoxubao
 * @Date 2020/7/16 14:44
 */

// 解析单行字符串
public class LineStringDecoder implements RespDecoder<String> {

    @Override
    public String decode(ByteBuf buffer) {
        return CodecUtils.X.readLine(buffer);
    }
}
