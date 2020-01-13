package com.baoge.netty.demo_009_zerocopy.new_zerocopy;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Copyright 2018-2028 Baoge All Rights Reserved.
 * Author: Shao Xu Bao <15818589952@163.com>
 * Date:   2020/1/12
 */
public class NewIOServer {
    public static void main(String[] args) throws Exception {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(8899);

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket(); // 服务端Socket
        serverSocket.setReuseAddress(true); // 若连接关闭了处于超时状态，设置true时可以绑定到这个超时状态ServerSocket
        serverSocket.bind(inetSocketAddress);

        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);

        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(true);

            int totalCount = 0;
            int readCount = 0;
            while (-1 != readCount) {
                readCount = socketChannel.read(byteBuffer);
                totalCount += readCount;

                // position归位为0
                byteBuffer.rewind();
            }

            System.out.println("服务端接收总字节数：" + totalCount);
        }
    }
}
