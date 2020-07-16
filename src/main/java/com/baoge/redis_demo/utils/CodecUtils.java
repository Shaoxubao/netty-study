package com.baoge.redis_demo.utils;

import com.baoge.redis_demo.constant.RespConstants;
import io.netty.buffer.ByteBuf;
import io.netty.util.ByteProcessor;

/**
 * @Author shaoxubao
 * @Date 2020/7/16 14:45
 */
public enum CodecUtils {
    X;

    public int findLineEndIndex(ByteBuf buffer) {
        int index = buffer.forEachByte(ByteProcessor.FIND_LF);
        return (index > 0 && buffer.getByte(index - 1) == '\r') ? index : -1;
    }

    public String readLine(ByteBuf buffer) {
        int lineEndIndex = findLineEndIndex(buffer);
        if (lineEndIndex > -1) {
            int lineStartIndex = buffer.readerIndex();
            // 计算字节长度
            int size = lineEndIndex - lineStartIndex - 1;
            byte[] bytes = new byte[size];
            buffer.readBytes(bytes);
            // 重置读游标为\r\n之后的第一个字节
            buffer.readerIndex(lineEndIndex + 1);
            buffer.markReaderIndex();
            return new String(bytes, RespConstants.UTF_8);
        }
        return null;
    }
}
