package com.baoge.netty.demo_008_nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * Copyright 2018-2028 Baoge All Rights Reserved.
 * Author: Shao Xu Bao <15818589952@163.com>
 * Date:   2020/1/5
 *
 * 关于Buffer的Scattering和Gathering
 * Scattering:将一个Channel中数据读取到多个Buffer，Gathering相反
 *
 * 通过telnet localhost 8890测试，或运行NioTest_11_Client
 */
public class NioTest11 {

    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(8890);
        serverSocketChannel.socket().bind(address);
        SocketChannel socketChannel = serverSocketChannel.accept();

        int messageLength = 2 + 3 + 4;
        ByteBuffer[] buffers = new ByteBuffer[3];
        buffers[0] = ByteBuffer.allocate(2);
        buffers[1] = ByteBuffer.allocate(3);
        buffers[2] = ByteBuffer.allocate(4);

        while (true) {
            long byteRead = 0;
            while (byteRead < messageLength) {
                long r = socketChannel.read(buffers);

                byteRead += r;

                System.out.println("byteRead: " + byteRead);

                Arrays.asList(buffers).stream().
                        map(buffer -> "position: " + buffer.position() + ", limit: " + buffer.limit()).
                        forEach(System.out::println);
            }

            Arrays.asList(buffers).forEach(byteBuffer -> {
                byteBuffer.flip();
            });

            long byteWritten = 0;
            while (byteWritten < messageLength) {
                long w = socketChannel.write(buffers);
                byteWritten += w;
            }

            Arrays.asList(buffers).forEach(byteBuffer -> {
                byteBuffer.clear();
            });

            System.out.println("end" + " byteRead: " + byteRead + ", byteWritten: " + byteWritten + ", messageLength: " + messageLength);
        }

    }

}
