package com.baoge.netty.demo_014_encode_decode;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.base64.Base64;

/**
 * @Author shaoxubao
 * @Date 2020/6/15 16:27
 */
public class DelimiterBasedFrameDecoderClient {
    public static void main(String[] args) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        // 在于server建立连接后，即发送请求报文
                        public void channelActive(ChannelHandlerContext ctx) {
                            // 先对要发送的原始内容进行base64编码
                            ByteBuf content = Base64.encode(Unpooled.buffer().writeBytes("hello&baoge&".getBytes()));
                            // 之后添加分隔符
                            ByteBuf req = Unpooled.copiedBuffer(content);
                            req.writeBytes("&".getBytes());
                            ctx.writeAndFlush(req);
                        }
                    });
                }
            });
            // Start the client.
            ChannelFuture f = b.connect("127.0.0.1",8080).sync();
            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
