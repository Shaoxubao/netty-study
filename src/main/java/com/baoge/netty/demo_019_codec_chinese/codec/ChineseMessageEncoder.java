package com.baoge.netty.demo_019_codec_chinese.codec;

import com.baoge.netty.demo_019_codec_chinese.ChineseMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Author shaoxubao
 * @Date 2020/7/14 14:26
 */
public class ChineseMessageEncoder extends MessageToByteEncoder<ChineseMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, ChineseMessage chineseMessage, ByteBuf out) {
        // 写入ID
        out.writeLong(chineseMessage.getId());
        String message = chineseMessage.getMessage();
        // 记录写入游标
        int writerIndex = out.writerIndex();
        // 预写入一个假的length
        out.writeInt(0);
        // 写入UTF-8字符序列，返回写入的字节长度 - 建议使用此方法
        int length = ByteBufUtil.writeUtf8(out, message);
        // 覆盖length
        out.setInt(writerIndex, length);
    }
}
