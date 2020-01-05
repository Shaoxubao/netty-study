package com.baoge.netty.demo_008_nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Copyright 2018-2028 Baoge All Rights Reserved.
 * Author: Shao Xu Bao <15818589952@163.com>
 * Date:   2020/1/6
 */
public class NioTest_11_Client {

    public static void main(String[] args) throws Exception {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 8890));

        String newData = "helloword";
//        String newData = "hello";

        ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        writeBuffer.clear();
        writeBuffer.put(newData.getBytes());

        writeBuffer.flip();

        while (writeBuffer.hasRemaining()) {
            socketChannel.write(writeBuffer);
        }

        int bytesRead = socketChannel.read(readBuffer);
        if (bytesRead > 0) {
            readBuffer.flip();
            byte[] bytes = new byte[bytesRead];
            readBuffer.get(bytes, 0, bytesRead);
            String str = new String(bytes);
            System.out.println(str);
            readBuffer.clear();
        }

        socketChannel.close();
    }

}
