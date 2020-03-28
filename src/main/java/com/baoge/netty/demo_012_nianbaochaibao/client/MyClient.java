package com.baoge.netty.demo_012_nianbaochaibao.client;

import com.baoge.netty.demo_012_nianbaochaibao.codec.MyPersonDecoder;
import com.baoge.netty.demo_012_nianbaochaibao.codec.MyPersonEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Copyright 2018-2028 Baoge All Rights Reserved.
 * Author: Shao Xu Bao <15818589952@163.com>
 * Date:   2020/1/1
 */
public class MyClient {

    public static void main(String[] args) throws Exception {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
            .channel(NioSocketChannel.class)
            .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();

                    pipeline.addLast(new MyPersonDecoder());
                    pipeline.addLast(new MyPersonEncoder());

                    pipeline.addLast(new MyClientHandler());
                }
            });

            ChannelFuture future = bootstrap.connect("localhost" + "", 8899).sync();
            future.channel().closeFuture().sync();

            System.out.println("MyClient started!!!");
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }

}
