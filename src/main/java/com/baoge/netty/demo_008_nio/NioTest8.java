package com.baoge.netty.demo_008_nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Copyright 2018-2028 Baoge All Rights Reserved.
 * Author: Shao Xu Bao <15818589952@163.com>
 * Date:   2020/1/5
 *
 * DirectByteBuffer :继承MappedByteBuffer——>ByteBuffer（long address）
 * address指向堆外内存的一个地址，堆外内存的创建和释放都是由DirectByteBuffer调用Java本地方法（JNI）实现的
 */
public class NioTest8 {

    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("input2.txt");
        FileOutputStream fileOutputStream = new FileOutputStream("output2.txt");

        FileChannel inputChannel = fileInputStream.getChannel();
        FileChannel outputChannel = fileOutputStream.getChannel();

        // 直接缓冲，堆外内存
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(521);
        while (true) {
            byteBuffer.clear();

            int read = inputChannel.read(byteBuffer);
            System.out.println("read: " + read);

            if (read < 0 || read == -1) {
                break;
            }

            // 关键一步
            byteBuffer.flip();

            outputChannel.write(byteBuffer);
        }

        fileInputStream.close();
        fileOutputStream.close();
    }

}
