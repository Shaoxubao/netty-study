package com.baoge.netty.demo_010_codec.client;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.time.LocalDateTime;

/**
 * Copyright 2018-2028 Baoge All Rights Reserved.
 * Author: Shao Xu Bao <15818589952@163.com>
 * Date:   2020/1/1
 */
public class MyClientHandler extends SimpleChannelInboundHandler<Long> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress());

        System.out.println("client receive: " + msg);

//        ctx.writeAndFlush("from client: " + LocalDateTime.now()); // 会被丢弃，因为不是Long类型
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        ctx.writeAndFlush(123456L); // 客户端启动后向服务端发送数据
//
//        ctx.writeAndFlush(1L);
//        ctx.writeAndFlush(2L);
//        ctx.writeAndFlush(3L);
//        ctx.writeAndFlush(4L);

//        ctx.writeAndFlush("hello world"); // 消息发送不出去（类型不匹配）

        // 消息发送出去（类型是ByteBuf）,但是编码器不执行（类型不匹配），服务端接收
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello world", Charset.forName("utf-8")));

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
