package com.baoge.redis_demo;

import com.baoge.redis_demo.constant.RespConstants;
import com.baoge.redis_demo.utils.CodecUtils;
import io.netty.buffer.ByteBuf;

/**
 * @Author shaoxubao
 * @Date 2020/7/16 14:54
 */
public class RespIntegerDecoder implements RespDecoder<Long> {

    @Override
    public Long decode(ByteBuf buffer) {
        int lineEndIndex = CodecUtils.X.findLineEndIndex(buffer);
        // 没有行尾，异常
        if (-1 == lineEndIndex) {
            return null;
        }
        long result = 0L;
        int lineStartIndex = buffer.readerIndex();
        boolean negative = false;
        byte firstByte = buffer.getByte(lineStartIndex);
        // 负数
        if (RespConstants.MINUS_BYTE == firstByte) {
            negative = true;
        } else {
            int digit = firstByte - '0';
            result = result * 10 + digit;
        }
        for (int i = lineStartIndex + 1; i < (lineEndIndex - 1); i++) {
            byte value = buffer.getByte(i);
            int digit = value - '0';
            result = result * 10 + digit;
        }
        if (negative) {
            result = -result;
        }
        // 重置读游标为\r\n之后的第一个字节
        buffer.readerIndex(lineEndIndex + 1);
        return result;
    }
}
