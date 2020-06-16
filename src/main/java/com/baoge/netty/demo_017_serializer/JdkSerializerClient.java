package com.baoge.netty.demo_017_serializer;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.util.Date;

/**
 * @Author shaoxubao
 * @Date 2020/6/16 14:35
 */
public class JdkSerializerClient {

    public static void main(String[] args) throws Exception {

        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup).channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new ObjectEncoder());
                            ch.pipeline().addLast(new ObjectDecoder(new ClassResolver() {
                                @Override
                                public Class<?> resolve(String className) throws ClassNotFoundException {
                                    return Class.forName(className);
                                }
                            }));

                            ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                // 在于server建立连接后，即发送请求报文
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    Request request = new Request();
                                    request.setRequest("i am a request.");
                                    request.setRequestTime(new Date());
                                    ctx.writeAndFlush(request);
                                }

                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    Response response = (Response) msg;
                                    System.out.println("receive response:" + response);
                                }
                            });
                        }
                    });

            // Start the client.
            ChannelFuture f = bootstrap.connect("127.0.0.1", 8080).sync();
            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }

    }

}
