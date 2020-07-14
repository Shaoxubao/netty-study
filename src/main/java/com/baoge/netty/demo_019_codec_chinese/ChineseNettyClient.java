package com.baoge.netty.demo_019_codec_chinese;

import com.baoge.netty.demo_019_codec_chinese.codec.ChineseMessageDecoder;
import com.baoge.netty.demo_019_codec_chinese.codec.ChineseMessageEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author shaoxubao
 * @Date 2020/7/14 14:28
 */

@Slf4j
public class ChineseNettyClient {

    public static void main(String[] args) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();

        try {
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.TCP_NODELAY, Boolean.TRUE)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new LengthFieldBasedFrameDecoder(1024, 0, 4, 0, 4));
                            pipeline.addLast(new LengthFieldPrepender(4));
                            pipeline.addLast(new ChineseMessageEncoder());
                            pipeline.addLast(new ChineseMessageDecoder());
                            pipeline.addLast(new SimpleChannelInboundHandler<ChineseMessage>() {
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    log.info("channelActive...");
                                }
                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, ChineseMessage message) {
                                    log.info("接收到服务端的响应:{}", message);
                                }
                            });
                        }
                    });
            ChannelFuture future = bootstrap.connect("localhost", 9090).sync();
            log.info("客户端启动成功...");
            Channel channel = future.channel();
            ChineseMessage message = new ChineseMessage();
            message.setId(1L);
            message.setMessage("张大狗");
            channel.writeAndFlush(message);
            future.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }

    }

}
