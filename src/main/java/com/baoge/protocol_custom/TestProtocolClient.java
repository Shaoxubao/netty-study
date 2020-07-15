package com.baoge.protocol_custom;

import com.alibaba.fastjson.JSON;
import com.baoge.protocol_custom.codec.RequestMessagePacketEncoder;
import com.baoge.protocol_custom.codec.ResponseMessagePacketDecoder;
import com.baoge.protocol_custom.entity.MessageType;
import com.baoge.protocol_custom.entity.ProtocolConstant;
import com.baoge.protocol_custom.entity.RequestMessagePacket;
import com.baoge.protocol_custom.entity.ResponseMessagePacket;
import com.baoge.protocol_custom.serializer.FastJsonSerializer;
import com.baoge.protocol_custom.util.SerialNumberUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author shaoxubao
 * @Date 2020/7/15 16:20
 */
@Slf4j
public class TestProtocolClient {

    public static void main(String[] args) throws Exception {
        int port = 9092;
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(workerGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, Boolean.TRUE);
            bootstrap.option(ChannelOption.TCP_NODELAY, Boolean.TRUE);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024, 0, 4, 0, 4));
                    ch.pipeline().addLast(new LengthFieldPrepender(4));
                    ch.pipeline().addLast(new RequestMessagePacketEncoder(FastJsonSerializer.X));
                    ch.pipeline().addLast(new ResponseMessagePacketDecoder());
                    ch.pipeline().addLast(new SimpleChannelInboundHandler<ResponseMessagePacket>() {
                        @Override
                        protected void channelRead0(ChannelHandlerContext ctx, ResponseMessagePacket packet) throws Exception {
                            Object targetPayload = packet.getPayload();
                            if (targetPayload instanceof ByteBuf) {
                                ByteBuf byteBuf = (ByteBuf) targetPayload;
                                int readableByteLength = byteBuf.readableBytes();
                                byte[] bytes = new byte[readableByteLength];
                                byteBuf.readBytes(bytes);
                                targetPayload = FastJsonSerializer.X.decode(bytes, String.class);
                                byteBuf.release();
                            }
                            packet.setPayload(targetPayload);
                            log.info("接收到来自服务端的响应消息,消息内容:{}", JSON.toJSONString(packet));
                        }
                    });
                }
            });
            ChannelFuture future = bootstrap.connect("localhost", port).sync();
            log.info("启动NettyClient[{}]成功...", port);
            Channel channel = future.channel();
            RequestMessagePacket packet = new RequestMessagePacket();
            packet.setMagicNumber(ProtocolConstant.MAGIC_NUMBER);
            packet.setVersion(ProtocolConstant.VERSION);
            packet.setSerialNumber(SerialNumberUtils.X.generateSerialNumber());
            packet.setMessageType(MessageType.REQUEST);
            packet.setInterfaceName("com.baoge.contract.HelloService");
            packet.setMethodName("sayHello");
            packet.setMethodArgumentSignatures(new String[]{"java.lang.String"});
            packet.setMethodArguments(new Object[]{"doge"});
            channel.writeAndFlush(packet);
            future.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
