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
 *
 * 在编写client端代码时我们先对原始内容进行base64编码，然后添加分割符之后进行输出。
 *
 * 需要注意，虽然Netty提供了Base64Encoder进行编码，这里并没有直接使用，如果直接使用Base64Encoder，那么会对我们输出的所有内容进行编码，
 * 意味着分隔符也会被编码，这显然不符合我们的预期，所以这里直接使用了Netty提供了Base64工具类来处理。
 *
 * 如果一定要使用Base64Encoder，那么代码需要进行相应的修改，自定义的ChannelInboundHandler只输出原始内容，之后通过Base64Encoder进行编码，
 * 然后需要额外再定义一个ChannelOutboundHandler添加分隔符，如：

    ch.pipeline().addLast(new ChannelOutboundHandlerAdapter() {
        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
            if(msg instanceof ByteBuf) {
                ((ByteBuf) msg).writeBytes("&".getBytes());
            }
        }
    });
    ch.pipeline().addLast(new Base64Encoder());
    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
        // 在于server建立连接后，即发送请求报文
        public void channelActive(ChannelHandlerContext ctx) {
            ByteBuf req = Unpooled.buffer().writeBytes("hello&tianshouzhi&".getBytes());
            ctx.writeAndFlush(req);
        }
    });

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
