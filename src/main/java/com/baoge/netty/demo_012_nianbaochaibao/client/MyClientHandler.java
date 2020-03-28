package com.baoge.netty.demo_012_nianbaochaibao.client;

import com.baoge.netty.demo_012_nianbaochaibao.PerdonProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

/**
 * Copyright 2018-2028 Baoge All Rights Reserved.
 * Author: Shao Xu Bao <xubao_shao@163.com>
 * Date:   2020/3/28
 */
public class MyClientHandler extends SimpleChannelInboundHandler<PerdonProtocol>  {

    private int count;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
            String message = "sent from client";

            int length = message.getBytes("utf-8").length;
            byte[] content = message.getBytes("utf-8");

            PerdonProtocol perdonProtocol = new PerdonProtocol();
            perdonProtocol.setLength(length);
            perdonProtocol.setContent(content);

            ctx.writeAndFlush(perdonProtocol);

        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PerdonProtocol msg) throws Exception {
        int length = msg.getLength();
        byte[] content = msg.getContent();

        System.out.println("客户端接收到的数据：");
        System.out.println("长度：" + length);
        System.out.println("内容：" + new String(content, Charset.forName("utf-8")));
        System.out.println("客户端接收到的数据数量：" + (++this.count));

     }
}
