package com.baoge.netty.demo_010_codec.client;

import com.baoge.netty.demo_010_codec.codec.MyByteToLongDecoder;
import com.baoge.netty.demo_010_codec.codec.MyByteToLongDecoder2;
import com.baoge.netty.demo_010_codec.codec.MyLongToByteEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * Copyright 2018-2028 Baoge All Rights Reserved.
 * Author: Shao Xu Bao <15818589952@163.com>
 * Date:   2020/1/1
 */
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

//        pipeline.addLast(new MyByteToLongDecoder());
        pipeline.addLast(new MyByteToLongDecoder2());
        pipeline.addLast(new MyLongToByteEncoder());

        pipeline.addLast(new MyClientHandler());
    }
}
