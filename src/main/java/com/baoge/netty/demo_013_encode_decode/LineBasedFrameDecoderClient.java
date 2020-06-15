package com.baoge.netty.demo_013_encode_decode;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.TimeUnit;

/**
 * @Author shaoxubao
 * @Date 2020/6/15 15:48
 *
 * 在client端，我们并没有使用LineEncoder进行编码，原因在于我们要模拟粘包、拆包。如果使用LineEncoder，那么每次调用ctx.write或者ctx.writeAndFlush，LineEncoder都会自动添加换行符，无法模拟拆包问题。
 * 我们通过自定义了一个ChannelInboundHandler，用于在连接建立后，发送3个请求报文req1、req2、req3。其中req1和req2都是一个完整的报文，
 * 因为二者都包含一个换行符；req3分两次发送，第一次发送req3_1，第二次发送req3_2。
 *
 * 有几点进行说明：
 *
 * 部分同学可能认为调用一个writeAndFlush方法就是发送了一个请求，这是对协议的理解不够深刻。一个完整的请求是由协议规定的，例如我们在这里使用了LineBasedFrameDecoder，
 * 潜在的含义就是：一行数据才算一个完整的报文。因此当你调用writeAndFlush方法，如果发送的数据有多个换行符，意味着相当于发送了多次有效请求；而如果发送的数据不包含换行符，
 * 意味着你的数据还不足以构成一个有效请求。
 *
 * 对于粘包问题，例如是两个有效报文粘在一起，那么服务端解码后，可以立即处理这两个报文。
 *
 * 对于拆包问题，例如一个报文是完整的，另一个只是半包，netty会对半包的数据进行缓存，等到可以构成一个完整的有效报文后，才会进行处理。这意味着么netty需要缓存每个client的半包数据，
 * 如果很多client都发送半包，缓存的数据就会占用大量内存空间。因此我们在实际开发中，不要像上面案例那样，有意将报文拆开来发送。
 *
 * 此外，如果client发送了半包，而剩余部分内容没有发送就关闭了，对于这种情况，netty服务端在销毁连接时，会自动清空之前缓存的数据，不会一直缓存。
 */
public class LineBasedFrameDecoderClient {

    public static void main(String[] args) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            // ch.pipeline().addLast(new LineEncoder());自己添加换行符，不使用LineEncoder
                            ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                // 在于server建立连接后，即发送请求报文
                                public void channelActive(ChannelHandlerContext ctx) {
                                    byte[] req1 = ("hello1" + System.getProperty("line.separator")).getBytes();
                                    byte[] req2 = ("hello2" + System.getProperty("line.separator")).getBytes();
                                    byte[] req3_1 = ("hello3").getBytes();
                                    byte[] req3_2 = (System.getProperty("line.separator")).getBytes();
                                    ByteBuf buffer = Unpooled.buffer();
                                    buffer.writeBytes(req1);
                                    buffer.writeBytes(req2);
                                    buffer.writeBytes(req3_1);
                                    ctx.writeAndFlush(buffer);
                                    try {
                                        TimeUnit.SECONDS.sleep(3);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    buffer = Unpooled.buffer();
                                    buffer.writeBytes(req3_2);
                                    ctx.writeAndFlush(buffer);
                                }
                            });
                        }
                    });
            ChannelFuture f = bootstrap.connect("127.0.0.1",8080).sync();
            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

}
