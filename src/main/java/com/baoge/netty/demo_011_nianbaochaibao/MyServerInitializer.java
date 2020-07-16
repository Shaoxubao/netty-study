package com.baoge.netty.demo_011_nianbaochaibao;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

/**
 * Copyright 2018-2028 Baoge All Rights Reserved.
 * Author: Shao Xu Bao <15818589952@163.com>
 * Date:   2020/1/1
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        System.out.println(this);

        ChannelPipeline pipeline = ch.pipeline();

        // 注释以下两句，会发生粘包现象，加上则不会
        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024, 0, 4, 0, 4));
        ch.pipeline().addLast(new LengthFieldPrepender(4));
        pipeline.addLast(new MyServerHandler()); // 业务处理器
    }
}
