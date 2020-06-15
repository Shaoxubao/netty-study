package com.baoge.netty.demo_015_encode_decode;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * @Author shaoxubao
 * @Date 2020/6/15 16:40

    FixedLengthFrameDecoder采用的是定长协议：即把固定的长度的字节数当做一个完整的消息

    例如，我们规定每3个字节，表示一个有效报文，如果我们分4次总共发送以下9个字节：

    +---+----+------+----+
    | A | BC | DEFG | HI |
    +---+----+------+----+
    那么通过FixedLengthFrameDecoder解码后，实际上只会解析出来3个有效报文

    +-----+-----+-----+
    | ABC | DEF | GHI |
    +-----+-----+-----+

 * FixedLengthFrameDecoder并没有提供一个对应的编码器，因为接收方只需要根据字节数进行判断即可，发送方无需编码
 */
public class FixedLengthFrameDecoderServer {
    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new FixedLengthFrameDecoder(3));
                            // 自定义这个ChannelInboundHandler打印拆包后的结果
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
            System.out.println("FixedLengthFrameDecoderServer Started on 8080...");
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
