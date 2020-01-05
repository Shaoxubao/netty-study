package com.baoge.netty.demo_007_decorator;

/**
 * Copyright 2018-2028 Baoge All Rights Reserved.
 * Author: Shao Xu Bao <15818589952@163.com>
 * Date:   2020/1/5
 */
public class Client {

    public static void main(String[] args) {

        Component component1 = new ConcreteComponent();
        component1.doSomething();

        System.out.println("=================");

        Component component0 = new ConcreteDecorator1(new ConcreteComponent());
        component0.doSomething();

        System.out.println("=================");

        Component component2 = new ConcreteDecorator2(
                new ConcreteDecorator1(new ConcreteComponent()));
        component2.doSomething();
    }

}
