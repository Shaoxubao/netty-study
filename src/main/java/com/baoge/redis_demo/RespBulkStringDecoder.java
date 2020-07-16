package com.baoge.redis_demo;

import com.baoge.redis_demo.constant.ReplyType;
import com.baoge.redis_demo.constant.RespConstants;
import com.baoge.redis_demo.utils.CodecUtils;
import io.netty.buffer.ByteBuf;

/**
 * @Author shaoxubao
 * @Date 2020/7/16 14:54
 *
 * 解析定长字符串
 */
public class RespBulkStringDecoder implements RespDecoder<String> {

    @Override
    public String decode(ByteBuf buffer) {
        int lineEndIndex = CodecUtils.X.findLineEndIndex(buffer);
        if (-1 == lineEndIndex) {
            return null;
        }
        Long length = (Long) DefaultRespCodec.DECODERS.get(ReplyType.INTEGER).decode(buffer);
        if (null == length) {
            return null;
        }
        // Bulk Null String
        if (RespConstants.NEGATIVE_ONE.equals(length)) {
            return null;
        }
        // Bulk Empty String
        if (RespConstants.ZERO.equals(length)) {
            return RespConstants.EMPTY_STRING;
        }
        // 真实字节内容的长度
        int readLength = (int) length.longValue();
        if (buffer.readableBytes() > readLength) {
            byte[] bytes = new byte[readLength];
            buffer.readBytes(bytes);
            // 重置读游标为\r\n之后的第一个字节
            buffer.readerIndex(buffer.readerIndex() + 2);
            return new String(bytes, RespConstants.UTF_8);
        }
        return null;
    }
}
