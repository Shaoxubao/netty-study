package com.baoge.netty.demo_012_nianbaochaibao.codec;

import com.baoge.netty.demo_012_nianbaochaibao.PerdonProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * Copyright 2018-2028 Baoge All Rights Reserved.
 * Author: Shao Xu Bao <xubao_shao@163.com>
 * Date:   2020/3/28
 */
public class MyPersonDecoder extends ReplayingDecoder<Void> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        System.out.println("MyPersonDecoder decode invoked.");
        int length = in.readInt();

        byte[] content = new byte[length];
        in.readBytes(content);

        PerdonProtocol perdonProtocol = new PerdonProtocol();
        perdonProtocol.setLength(length);
        perdonProtocol.setContent(content);

        out.add(perdonProtocol);
    }
}
