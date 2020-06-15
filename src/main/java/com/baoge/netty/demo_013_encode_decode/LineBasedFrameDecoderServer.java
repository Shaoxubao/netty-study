package com.baoge.netty.demo_013_encode_decode;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * @Author shaoxubao
 * @Date 2020/6/15 15:34
 *
 * LineBasedFrameDecoder / LineEncoder使用案例:
 * LineBasedFrameDecoder要解决粘包问题，根据"\n"或"\r\n"对二进制数据进行解码，可能会解析出多个完整的请求报文，其会将每个有效报文封装在不同的ByteBuf实例中，
 * 然后针对每个ByteBuf实例都会调用一次其他的ChannelInboundHandler的channelRead方法。
 *
 * 因此LineBasedFrameDecoder接受到一次数据，其之后的ChannelInboundHandler的channelRead方法可能会被调用多次，且之后的ChannelInboundHandler的
 * channelRead方法接受到的ByteBuf实例参数，包含的都是都是一个完整报文的二进制数据。因此无需再处理粘包问题，只需要将ByteBuf中包含的请求信息解析出来即可，
 * 然后进行相应的处理。本例中，我们仅仅是打印。
 */
public class LineBasedFrameDecoderServer {

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
                            // 使用LineBasedFrameDecoder解决粘包问题，其会根据"\n"或"\r\n"对二进制数据进行拆分，封装到不同的ByteBuf实例中
                            ch.pipeline().addLast(new LineBasedFrameDecoder(1024, true, true));
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

            ChannelFuture future = serverBootstrap.bind(8080).sync();
            System.out.println("LineBasedFrameDecoderServer Started on 8080...");
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
