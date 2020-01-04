package com.baoge.protobuf;

/**
 * Copyright 2018-2028 Baoge All Rights Reserved.
 * Author: Shao Xu Bao <15818589952@163.com>
 * Date:   2020/1/4
 */
public class ProtoBufTest {

    public static void main(String[] args) throws Exception {
        DataInfo.Student student = DataInfo.Student.newBuilder().setName("张三").setAge(20).setAddress("中国").build();

        byte[] student2ByteArray = student.toByteArray();

        DataInfo.Student student1 = DataInfo.Student.parseFrom(student2ByteArray);

        System.out.println("name:" + student1.getName());
        System.out.println("age:" + student1.getAge());
        System.out.println("address:" + student1.getAddress());
    }

}
