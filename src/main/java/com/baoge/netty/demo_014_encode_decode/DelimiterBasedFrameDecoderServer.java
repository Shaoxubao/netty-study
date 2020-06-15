package com.baoge.netty.demo_014_encode_decode;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.base64.Base64Decoder;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * @Author shaoxubao
 * @Date 2020/6/15 16:23
 * <p>
 * DelimiterBasedFrameDecoder结合Base64编解码案例
 */
public class DelimiterBasedFrameDecoderServer {
    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ByteBuf delimiter = Unpooled.buffer();
                            delimiter.writeBytes("&".getBytes());
                            // 先使用DelimiterBasedFrameDecoder解码，以&作为分割符
                            ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, true, true, delimiter));
                            // 之后使用Base64Decoder对数据进行解码，得到报文的原始的二进制流
                            ch.pipeline().addLast(new Base64Decoder());
                            // 对请求报文进行处理
                            ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    if (msg instanceof ByteBuf) {
                                        ByteBuf packet = (ByteBuf) msg;
                                        System.out.println(new Date().toString() + ":" + packet.toString(Charset.defaultCharset()));
                                    }
                                }
                            });
                        }
                    });
            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(8080).sync();
            System.out.println("DelimiterBasedFrameDecoderServer Started on 8080...");
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
