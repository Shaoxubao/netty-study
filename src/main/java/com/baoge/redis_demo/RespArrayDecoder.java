package com.baoge.redis_demo;

import com.baoge.redis_demo.constant.ReplyType;
import com.baoge.redis_demo.constant.RespConstants;
import com.baoge.redis_demo.utils.CodecUtils;
import com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;

import java.util.List;

/**
 * @Author shaoxubao
 * @Date 2020/7/16 14:54
 */

public class RespArrayDecoder implements RespDecoder {

    @Override
    public Object decode(ByteBuf buffer) {
        int lineEndIndex = CodecUtils.X.findLineEndIndex(buffer);
        if (-1 == lineEndIndex) {
            return null;
        }
        // 解析元素个数
        Long length = (Long) DefaultRespCodec.DECODERS.get(ReplyType.INTEGER).decode(buffer);
        if (null == length) {
            return null;
        }
        // Null Array
        if (RespConstants.NEGATIVE_ONE.equals(length)) {
            return null;
        }
        // Array Empty List
        if (RespConstants.ZERO.equals(length)) {
            return Lists.newArrayList();
        }
        List<Object> result = Lists.newArrayListWithCapacity((int) length.longValue());
        // 递归
        for (int i = 0; i < length; i++) {
            result.add(DefaultRespCodec.X.decode(buffer));
        }
        return result;
    }
}
