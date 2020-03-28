package com.baoge.netty.demo_012_nianbaochaibao;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Copyright 2018-2028 Baoge All Rights Reserved.
 * Author: Shao Xu Bao <15818589952@163.com>
 * Date:   2020/1/1
 */
public class MyServer {

    public static void main(String[] args) throws Exception {
        // NioEventLoopGroup：异步事件循环组，可加参数，表示线程数
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);   // 接收客户端的连接
        EventLoopGroup workerGroup = new NioEventLoopGroup(); // 正真完成用户请求

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new MyServerInitializer());

            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();

            System.out.println("MyServer started!!!");
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

}
