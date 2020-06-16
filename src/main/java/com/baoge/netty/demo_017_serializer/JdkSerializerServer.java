package com.baoge.netty.demo_017_serializer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.util.Date;

/**
 * @Author shaoxubao
 * @Date 2020/6/16 14:48
 */
public class JdkSerializerServer {

    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new ObjectEncoder());
                            ch.pipeline().addLast(new ObjectDecoder(new ClassResolver() {
                                @Override
                                public Class<?> resolve(String className) throws ClassNotFoundException {
                                    return Class.forName(className);
                                }
                            }));
                            // 自定义这个ChannelInboundHandler打印拆包后的结果
                            ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    Request request = (Request) msg;
                                    System.out.println("receive request:" + request);
                                    Response response = new Response();
                                    response.setResponse("response to:" + request.getRequest());
                                    response.setResponseTime(new Date());
                                    ctx.writeAndFlush(response);
                                }
                            });
                        }
                    });
            // Bind and start to accept incoming connections.
            ChannelFuture f = serverBootstrap.bind(8080).sync();
            System.out.println("JdkSerializerServer Started on 8080...");
            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

}
