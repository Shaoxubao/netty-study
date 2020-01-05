package com.baoge.netty.demo_008_nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Copyright 2018-2028 Baoge All Rights Reserved.
 * Author: Shao Xu Bao <15818589952@163.com>
 * Date:   2020/1/5
 */
public class NioTest4 {

    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("input.txt");
        FileOutputStream fileOutputStream = new FileOutputStream("output.txt");

        FileChannel inputChannel = fileInputStream.getChannel();
        FileChannel outputChannel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(521);
        while (true) {
            byteBuffer.clear(); // 如果注释此句，会死循环，再次进入循环读取到buffer时read等于0，
                                // 原因是不调用clear()时，position和limit的值一直指向第一次读取的位置，
                                // 再调用read()时，read值是0，调用flip()后再write()，又把第一次读取的数据写入到outputChannel

            int read = inputChannel.read(byteBuffer);
            System.out.println("read: " + read);

            if (-1 == read) {
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
