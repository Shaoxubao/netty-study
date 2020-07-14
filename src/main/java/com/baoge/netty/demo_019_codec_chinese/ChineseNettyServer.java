package com.baoge.netty.demo_019_codec_chinese;

import com.baoge.netty.demo_019_codec_chinese.codec.ChineseMessageDecoder;
import com.baoge.netty.demo_019_codec_chinese.codec.ChineseMessageEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author shaoxubao
 * @Date 2020/7/14 14:39
 */

@Slf4j
public class ChineseNettyServer {

    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        try {
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024, 0, 4, 0, 4));
                            ch.pipeline().addLast(new LengthFieldPrepender(4));
                            ch.pipeline().addLast(new ChineseMessageEncoder());
                            ch.pipeline().addLast(new ChineseMessageDecoder());
                            ch.pipeline().addLast(new SimpleChannelInboundHandler<ChineseMessage>() {

                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    log.info("channelActive...");
                                }

                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, ChineseMessage message) throws Exception {
                                    log.info("接收到客户端的请求:{}", message);
                                    ChineseMessage chineseMessage = new ChineseMessage();
                                    chineseMessage.setId(message.getId() + 1L);
                                    chineseMessage.setMessage("张小狗");
                                    ctx.writeAndFlush(chineseMessage);
                                }
                            });
                        }
                    });
            ChannelFuture future = serverBootstrap.bind(9090).sync();
            log.info("启动Server成功...");
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
