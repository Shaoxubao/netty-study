package com.baoge.protocol_custom;

import com.alibaba.fastjson.JSON;
import com.baoge.protocol_custom.codec.RequestMessagePacketDecoder;
import com.baoge.protocol_custom.codec.ResponseMessagePacketEncoder;
import com.baoge.protocol_custom.entity.MessageType;
import com.baoge.protocol_custom.entity.RequestMessagePacket;
import com.baoge.protocol_custom.entity.ResponseMessagePacket;
import com.baoge.protocol_custom.serializer.FastJsonSerializer;
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
 * @Date 2020/7/15 16:26
 */
@Slf4j
public class TestProtocolServer {

    public static void main(String[] args) throws Exception {
        int port = 9092;
        ServerBootstrap bootstrap = new ServerBootstrap();
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024, 0, 4, 0, 4));
                            ch.pipeline().addLast(new LengthFieldPrepender(4));
                            ch.pipeline().addLast(new RequestMessagePacketDecoder());
                            ch.pipeline().addLast(new ResponseMessagePacketEncoder(FastJsonSerializer.X));
                            ch.pipeline().addLast(new SimpleChannelInboundHandler<RequestMessagePacket>() {
                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, RequestMessagePacket packet) throws Exception {
                                    log.info("接收到来自客户端的请求消息,消息内容:{}", JSON.toJSONString(packet));
                                    ResponseMessagePacket response = new ResponseMessagePacket();
                                    response.setMagicNumber(packet.getMagicNumber());
                                    response.setVersion(packet.getVersion());
                                    response.setSerialNumber(packet.getSerialNumber());
                                    response.setAttachments(packet.getAttachments());
                                    response.setMessageType(MessageType.RESPONSE);
                                    response.setErrorCode(200L);
                                    response.setMessage("Success");
                                    response.setPayload("{\"name\":\"baoge\"}");
                                    ctx.writeAndFlush(response);
                                }
                            });
                        }
                    });
            ChannelFuture future = bootstrap.bind(port).sync();
            log.info("启动NettyServer[{}]成功...", port);
            future.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
