package com.baoge.netty.demo_009_zerocopy.old_zerocopy;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Copyright 2018-2028 Baoge All Rights Reserved.
 * Author: Shao Xu Bao <15818589952@163.com>
 * Date:   2020/1/12
 */
public class OldIOServer {

    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(8899);

        while (true) {
            Socket socket = serverSocket.accept();
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

            byte[] byteArr = new byte[4096];
            while (true) {
                int readCount = dataInputStream.read(byteArr, 0, byteArr.length);
                if (-1 == readCount) {
                    break;
                }
            }
        }
    }

}
