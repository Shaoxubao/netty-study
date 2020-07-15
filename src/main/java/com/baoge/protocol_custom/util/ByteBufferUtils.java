package com.baoge.protocol_custom.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;

/**
 * @Author shaoxubao
 * @Date 2020/7/15 16:10
 */
public enum ByteBufferUtils {

    // 单例
    X;

    public void encodeUtf8CharSequence(ByteBuf byteBuf, CharSequence charSequence) {
        int writerIndex = byteBuf.writerIndex();
        byteBuf.writeInt(0);
        int length = ByteBufUtil.writeUtf8(byteBuf, charSequence);
        byteBuf.setInt(writerIndex, length);
    }
}
