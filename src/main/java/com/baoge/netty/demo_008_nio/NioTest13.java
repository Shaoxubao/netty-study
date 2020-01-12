package com.baoge.netty.demo_008_nio;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * Copyright 2018-2028 Baoge All Rights Reserved.
 * Author: Shao Xu Bao <15818589952@163.com>
 * Date:   2020/1/12
 *
 * 字符集编码
 */
public class NioTest13 {

    public static void main(String[] args) throws Exception {
        String inputFile = "NioTest13_In.txt";
        String outputFile = "NioTest13_Out.txt";

        RandomAccessFile inputRandomAccessFile = new RandomAccessFile(inputFile,"r");
        RandomAccessFile outputRandomAccessFile = new RandomAccessFile(outputFile, "rw");

        long inputLength = new File(inputFile).length();

        // 获取输入输出channel
        FileChannel inputFileChannel = inputRandomAccessFile.getChannel();
        FileChannel outputFileChannel = outputRandomAccessFile.getChannel();

        // 内存映射
        MappedByteBuffer inputData = inputFileChannel.map(FileChannel.MapMode.READ_ONLY, 0, inputLength);

        System.out.println("----------------");
        Charset.availableCharsets().forEach((k, v) -> {
            System.out.println(k + ", " + v);
        });
        System.out.println("----------------");

        // 字符集编码
        Charset charset = Charset.forName("utf-8"); // 使用iso-8859-1时也可以成功拷贝运行中文字符
        CharsetDecoder decoder = charset.newDecoder();
        CharsetEncoder encoder = charset.newEncoder();

        // 解码
        CharBuffer charBuffer = decoder.decode(inputData);
        // 编码
        ByteBuffer outputData = encoder.encode(charBuffer);

        outputFileChannel.write(outputData);

        inputFileChannel.close();
        outputFileChannel.close();
    }

}
