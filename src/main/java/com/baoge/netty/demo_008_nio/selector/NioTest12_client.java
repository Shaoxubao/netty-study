package com.baoge.netty.demo_008_nio.selector;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Copyright 2018-2028 Baoge All Rights Reserved.
 * Author: Shao Xu Bao <15818589952@163.com>
 * Date:   2020/1/6
 */
public class NioTest12_client {

    public static void main(String[] args) throws Exception {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 5000));

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String data = reader.readLine() + "\r\n";

        ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        writeBuffer.clear();
        writeBuffer.put(data.getBytes());

        writeBuffer.flip();

        while (writeBuffer.hasRemaining()) {
            socketChannel.write(writeBuffer);
        }

        int bytesRead = socketChannel.read(readBuffer);
        if (bytesRead > 0) {
            readBuffer.flip();
            byte[] bytes = new byte[bytesRead];
            readBuffer.get(bytes, 0, bytesRead);
            System.out.println(new String(bytes));
            readBuffer.clear();
        }

        socketChannel.close();
    }
}

