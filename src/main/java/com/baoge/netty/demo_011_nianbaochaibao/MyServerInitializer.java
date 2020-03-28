package com.baoge.netty.demo_011_nianbaochaibao;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

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

        pipeline.addLast(new MyServerHandler()); // 业务处理器
    }
}
