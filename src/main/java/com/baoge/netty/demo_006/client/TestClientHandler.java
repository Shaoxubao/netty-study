package com.baoge.netty.demo_006.client;

import com.baoge.protobuf.DataInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

/**
 * Copyright 2018-2028 Baoge All Rights Reserved.
 * Author: Shao Xu Bao <15818589952@163.com>
 * Date:   2020/1/4
 */
public class TestClientHandler extends SimpleChannelInboundHandler<DataInfo.MyMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DataInfo.MyMessage msg) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        DataInfo.Student student = DataInfo.Student.newBuilder()
//                .setName("张三").setAge(20).setAddress("中国").build();
//        ctx.channel().writeAndFlush(student);

        DataInfo.MyMessage myMessage = null;
        int randomInt = new Random().nextInt(3);
        if (randomInt == 0) {
            myMessage = DataInfo.MyMessage.newBuilder()
                    .setDataType(DataInfo.MyMessage.DataType.StudentType)
                    .setStudent(DataInfo.Student.newBuilder()
                            .setName("张三")
                            .setAge(20)
                            .setAddress("中国").build())
                    .build();
        } else if (randomInt == 1) {
            myMessage = DataInfo.MyMessage.newBuilder()
                    .setDataType(DataInfo.MyMessage.DataType.DogType)
                    .setDog(DataInfo.Dog.newBuilder()
                            .setName("张三")
                            .setAge(20).build())
                    .build();
        } else {
            myMessage = DataInfo.MyMessage.newBuilder()
                    .setDataType(DataInfo.MyMessage.DataType.CatType)
                    .setCat(DataInfo.Cat.newBuilder()
                            .setName("张三")
                            .setCity("深圳市").build())
                    .build();
        }


        ctx.channel().writeAndFlush(myMessage);
    }
}
