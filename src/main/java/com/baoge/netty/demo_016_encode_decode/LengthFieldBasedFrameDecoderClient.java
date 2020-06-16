package com.baoge.netty.demo_016_encode_decode;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @Author shaoxubao
 * @Date 2020/6/15 16:56
 *
 * LengthFieldPrepender(编码)功能说明：
 * 如果协议中的第一个字段为长度字段，netty提供了LengthFieldPrepender编码器，它可以计算当前待发送消息的二进制字节长度，将该长度添加到ByteBuf的缓冲区头中
 * 例如：对于以下包含12个字节的报文
 *
 *    +----------------+
 *    | "HELLO, WORLD" |
 *    +----------------+
 * 假设我们指定Length字段占用2个字节，lengthIncludesLengthFieldLength指定为false，即不包含本身占用的字节，那么Length字段的值为0x000C(即12)。
 *
 *   +--------+----------------+
 *   + 0x000C | "HELLO, WORLD" |
 *   +--------+----------------+
 * 如果我们指定lengthIncludesLengthFieldLength指定为true，那么Length字段的值为：0x000E(即14)=Length(2)+Content字段(12)
 *
 *   +--------+----------------+
 *   + 0x000E | "HELLO, WORLD" |
 *   +--------+----------------+
 */
public class LengthFieldBasedFrameDecoderClient {
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
                    ch.pipeline().addLast(new LengthFieldPrepender(2, 0, false));
                    ch.pipeline().addLast(new StringEncoder());
                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        // 在于server建立连接后，即发送请求报文
                        public void channelActive(ChannelHandlerContext ctx) {
                            ctx.writeAndFlush("i am request!");
//                            ctx.writeAndFlush("i am a anther request!");
                        }
                    });
                }
            });
            // Start the client.
            ChannelFuture f = b.connect("127.0.0.1", 8080).sync();
            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
