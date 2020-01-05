package com.baoge.netty.demo_008_nio;

import java.nio.ByteBuffer;

/**
 * Copyright 2018-2028 Baoge All Rights Reserved.
 * Author: Shao Xu Bao <15818589952@163.com>
 * Date:   2020/1/5
 *
 * 只读Buffer
 */
public class NioTest7 {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        System.out.println(byteBuffer.getClass()); // class java.nio.HeapByteBuffer

        for (int i= 0; i < byteBuffer.capacity(); i++) {
            byteBuffer.put((byte) i);
        }

        ByteBuffer readonlyBuffer = byteBuffer.asReadOnlyBuffer();
        System.out.println(readonlyBuffer.getClass()); // class java.nio.HeapByteBufferR

//        readonlyBuffer.position(0);
//        readonlyBuffer.put((byte) 2); // 抛出异常ReadOnlyBufferException

    }
}
