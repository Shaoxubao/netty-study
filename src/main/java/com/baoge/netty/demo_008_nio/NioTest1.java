package com.baoge.netty.demo_008_nio;

import java.nio.IntBuffer;
import java.security.SecureRandom;

/**
 * Copyright 2018-2028 Baoge All Rights Reserved.
 * Author: Shao Xu Bao <15818589952@163.com>
 * Date:   2020/1/5
 */
public class NioTest1 {
    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.allocate(10);

        System.out.println("init capacity: " + buffer.capacity());
        System.out.println("init limit: " + buffer.limit());
        System.out.println("init position: " + buffer.position());

        System.out.println("=======================");

        for (int i = 0; i < 6; i++) {
            int randomInt = new SecureRandom().nextInt(20);
            buffer.put(randomInt);
        }

        System.out.println("before flip capacity: " + buffer.capacity());
        System.out.println("before flip limit: " + buffer.limit());
        System.out.println("before flip position: " + buffer.position());

        System.out.println("=======================");

        buffer.flip();

        System.out.println("after flip capacity: " + buffer.capacity());
        System.out.println("after flip limit: " + buffer.limit());
        System.out.println("after flip position: " + buffer.position());

        System.out.println("=======================");

        while (buffer.hasRemaining()) {
            System.out.println("enter while loop capacity: " + buffer.capacity());
            System.out.println("enter while loop limit: " + buffer.limit());
            System.out.println("enter while loop position: " + buffer.position());

            System.out.println(buffer.get());
        }

    }
}
