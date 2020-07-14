package com.baoge.netty.demo_012_nianbaochaibao;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;

/**
 * Copyright 2018-2028 Baoge All Rights Reserved.
 * Author: Shao Xu Bao <xubao_shao@163.com>
 * Date:   2020/3/28
 */
public class MyServerHandler extends SimpleChannelInboundHandler<PersonProtocol> {

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PersonProtocol msg) throws Exception {
        int length = msg.getLength();
        byte[] content = msg.getContent();

        System.out.println("服务端接收到数据：");
        System.out.println("长度：" + length);
        System.out.println("内容：" + new String(content, Charset.forName("utf-8")));
        System.out.println("服务端接收到数据数量：" + (++this.count));

        String responseMessage = UUID.randomUUID().toString();
        int responseLength = responseMessage.getBytes("utf-8").length;
        byte[] responseContent = responseMessage.getBytes("utf-8");

        PersonProtocol responseProtocol = new PersonProtocol();
        responseProtocol.setLength(responseLength);
        responseProtocol.setContent(responseContent);

        ctx.writeAndFlush(responseProtocol);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
