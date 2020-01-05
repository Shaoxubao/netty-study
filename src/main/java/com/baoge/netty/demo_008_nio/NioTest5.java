package com.baoge.netty.demo_008_nio;

import java.nio.ByteBuffer;

/**
 * Copyright 2018-2028 Baoge All Rights Reserved.
 * Author: Shao Xu Bao <15818589952@163.com>
 * Date:   2020/1/5
 *
 * ByteBuffer的类型化的put和get
 */
public class NioTest5 {

    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        byteBuffer.putInt(16);
        byteBuffer.putLong(89L);
        byteBuffer.putDouble(99.99);
        byteBuffer.putChar('我');
        byteBuffer.putShort((short) 2);

        byteBuffer.flip();

        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getLong());
        System.out.println(byteBuffer.getDouble());
        System.out.println(byteBuffer.getChar());
        System.out.println(byteBuffer.getShort());

    }

}
