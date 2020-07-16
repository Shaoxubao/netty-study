package com.baoge.redis_demo.test_main;

import com.baoge.redis_demo.RespCodec;
import com.baoge.redis_demo.constant.RespConstants;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Author shaoxubao
 * @Date 2020/7/16 14:47
 *
 * 在RESP中，数据类型取决于数据报的第一个字节：
 *
 * 单行字符串的第一个字节为+
 * 错误消息的第一个字节为-
 * 整型数字的第一个字节为:
 * 定长字符串的第一个字节为$
 * RESP数组的第一个字节为*
 */

@Slf4j
public class DecoderTest {

    public static void main(String[] args) {

        // 解析单行字符串
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        // +OK\r\n
        buffer.writeBytes("+OK".getBytes(RespConstants.UTF_8));
        buffer.writeBytes(RespConstants.CRLF);
        String value = RespCodec.X.decode(buffer);
        log.info("Decode result:{}", value);


        // 解析错误消息
        ByteBuf buffer2 = ByteBufAllocator.DEFAULT.buffer();
        // -ERR unknown command 'foobar'\r\n
        buffer2.writeBytes("-ERR unknown command 'foobar'".getBytes(RespConstants.UTF_8));
        buffer2.writeBytes(RespConstants.CRLF);
        String value2 = RespCodec.X.decode(buffer2);
        log.info("Decode result2:{}", value2);

        // 解析整型数字
        ByteBuf buffer3 = ByteBufAllocator.DEFAULT.buffer();
        // :-1000\r\n
        buffer3.writeBytes(":-1000".getBytes(RespConstants.UTF_8));
        buffer3.writeBytes(RespConstants.CRLF);
        Long value3 = RespCodec.X.decode(buffer3);
        log.info("Decode result3:{}", value3);

        // 解析定长字符串
        ByteBuf buffer4 = ByteBufAllocator.DEFAULT.buffer();
        // $9\r\nthrowable\r\n
        buffer4.writeBytes("$9".getBytes(RespConstants.UTF_8));
        buffer4.writeBytes(RespConstants.CRLF);
        buffer4.writeBytes("throwable".getBytes(RespConstants.UTF_8));
        buffer4.writeBytes(RespConstants.CRLF);
        String value4 = RespCodec.X.decode(buffer4);
        log.info("Decode result4:{}", value4);

        // 解析RESP数组
        ByteBuf buffer5 = ByteBufAllocator.DEFAULT.buffer();
        // *2\r\n$3\r\nfoo\r\n$3\r\nbar\r\n
        buffer5.writeBytes("*2".getBytes(RespConstants.UTF_8));
        buffer5.writeBytes(RespConstants.CRLF);
        buffer5.writeBytes("$3".getBytes(RespConstants.UTF_8));
        buffer5.writeBytes(RespConstants.CRLF);
        buffer5.writeBytes("foo".getBytes(RespConstants.UTF_8));
        buffer5.writeBytes(RespConstants.CRLF);
        buffer5.writeBytes("$3".getBytes(RespConstants.UTF_8));
        buffer5.writeBytes(RespConstants.CRLF);
        buffer5.writeBytes("bar".getBytes(RespConstants.UTF_8));
        buffer5.writeBytes(RespConstants.CRLF);
        List value5 = RespCodec.X.decode(buffer5);
        log.info("Decode result5:{}", value5);
    }

}
