package com.baoge.netty.demo_007_decorator;

/**
 * Copyright 2018-2028 Baoge All Rights Reserved.
 * Author: Shao Xu Bao <15818589952@163.com>
 * Date:   2020/1/5
 */
public class Client {

    public static void main(String[] args) {
        Component component = new ConcreteDecorator2(
                new ConcreteDecorator1(new ConcreteComponent()));

        component.doSomething();
    }

}
