package com.baoge.netty.demo_012_nianbaochaibao.codec;

import com.baoge.netty.demo_012_nianbaochaibao.PersonProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Copyright 2018-2028 Baoge All Rights Reserved.
 * Author: Shao Xu Bao <xubao_shao@163.com>
 * Date:   2020/3/28
 */
public class MyPersonEncoder extends MessageToByteEncoder<PersonProtocol> {

    @Override
    protected void encode(ChannelHandlerContext ctx, PersonProtocol msg, ByteBuf out) {
        System.out.println("MyPersonEncoder encode invoked.");

        out.writeInt(msg.getLength());
        out.writeBytes(msg.getContent());

    }
}
