package com.baoge.netty.demo_016_encode_decode;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @Author shaoxubao
 * @Date 2020/6/15 16:59
 *
 * LengthFieldBasedFrameDecoder作用实际上只是帮我们处理粘包和半包的问题，其只负责将可以构成一个完整有效的请求报文封装到ByteBuf中，之后还要依靠其他的解码器对报文的内容进行解析，
 * 例如上面编写的String将其解析为字符串，只不过在后续的解码器中，不需要处理粘包半包问题了，认为ByteBuf中包含的内容肯定是一个完整的报文即可。
 */
public class LengthFieldBasedFrameDecoderServer {
    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(16384, 0, 2, 0, 2));
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    System.out.println("receive req:" + msg);
                                }
                            });
                        }
                    });
            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(8080).sync();
            System.out.println("LengthFieldBasedFrameDecoderServer Started on 8080...");
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
