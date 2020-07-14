package com.baoge.netty.demo_019_codec_chinese.codec;

import com.baoge.netty.demo_019_codec_chinese.ChineseMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @Author shaoxubao
 * @Date 2020/7/14 14:27
 */
public class ChineseMessageDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        long id = in.readLong();
        int length = in.readInt();
        CharSequence charSequence = in.readCharSequence(length, StandardCharsets.UTF_8);
        ChineseMessage message = new ChineseMessage();
        message.setId(id);
        message.setMessage(charSequence.toString());
        out.add(message);
    }
}
