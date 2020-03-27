package com.baoge.netty.demo_010_codec;

import com.baoge.netty.demo_010_codec.codec.MyByteToLongDecoder;
import com.baoge.netty.demo_010_codec.codec.MyLongToByteEncoder;
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
        ChannelPipeline pipeline = ch.pipeline();

        // 自定义解码器，将接收客户端的字节流解码成Long类型
        pipeline.addLast(new MyByteToLongDecoder());
        pipeline.addLast(new MyLongToByteEncoder());

        pipeline.addLast(new MyServerHandler()); // 业务处理器
    }
}
