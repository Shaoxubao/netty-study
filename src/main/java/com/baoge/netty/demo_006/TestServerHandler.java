package com.baoge.netty.demo_006;

import com.baoge.protobuf.DataInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Copyright 2018-2028 Baoge All Rights Reserved.
 * Author: Shao Xu Bao <15818589952@163.com>
 * Date:   2020/1/4
 */
public class TestServerHandler extends SimpleChannelInboundHandler<DataInfo.MyMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DataInfo.MyMessage msg) throws Exception {
        DataInfo.MyMessage.DataType dataType = msg.getDataType();
        if (dataType == DataInfo.MyMessage.DataType.StudentType) {
            DataInfo.Student student = msg.getStudent();
            System.out.println("name:" + student.getName());
            System.out.println("age:" + student.getAge());
            System.out.println("address:" + student.getAddress());
        } else if (dataType == DataInfo.MyMessage.DataType.DogType) {
            DataInfo.Dog dog = msg.getDog();
            System.out.println("name:" + dog.getName());
            System.out.println("age:" + dog.getAge());
        } else {
            DataInfo.Cat cat = msg.getCat();
            System.out.println("name:" + cat.getName());
            System.out.println("city:" + cat.getCity());
        }

    }
}
