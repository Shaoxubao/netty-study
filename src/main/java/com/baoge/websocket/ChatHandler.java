package com.baoge.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;

/**
 * Copyright 2018-2028 Baoge All Rights Reserved.
 * Author: Shao Xu Bao <xubao_shao@163.com>
 * Date:   2020/6/20
 *
 * TextWebSocketFrame：在netty中，适用于为websocket专门处理文本的对象，frame是消息的载体
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    // 用于记录和管理所有客户端的channel
    private static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        // 获取客户端传输的消息内容
        String text = msg.text();
        System.out.println("接收到消息：" + text);

//        for (Channel channel : clients) {
//            channel.writeAndFlush(new TextWebSocketFrame("服务器在" + LocalDateTime.now() +
//                    "接受到消息为：" + text));
//        }

        // 或者这样写
        clients.writeAndFlush(new TextWebSocketFrame("服务器在" + LocalDateTime.now() +
                "接受到消息为：" + text));
    }

    /**
     * 将客户端的channel放大ChannelGroup进行管理
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        clients.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        // 当触发此方法，ChannelGroup会自动移除对应客户端的channel
//        clients.remove(ctx.channel());
        System.out.println("客户端断开，channel对应长id为：" + ctx.channel().id().asLongText());
        System.out.println("客户端断开，channel对应短id为：" + ctx.channel().id().asShortText());
    }
}
