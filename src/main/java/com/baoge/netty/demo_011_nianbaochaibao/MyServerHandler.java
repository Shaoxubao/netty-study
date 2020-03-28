package com.baoge.netty.demo_011_nianbaochaibao;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;

/**
 * Copyright 2018-2028 Baoge All Rights Reserved.
 * Author: Shao Xu Bao <15818589952@163.com>
 * Date:   2020/1/1
 */
public class MyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] buffer = new byte[msg.readableBytes()];
        msg.readBytes(buffer);

        String message = new String(buffer, Charset.forName("utf-8"));

        System.out.println("服务端接收到消息内容：" + message);
        System.out.println("服务端接收到消息数量：" + (++this.count));

        ByteBuf responseByteBuf = Unpooled.copiedBuffer(UUID.randomUUID().toString(), Charset.forName("utf-8"));

        ctx.writeAndFlush(responseByteBuf);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
