package com.baoge.netty.demo_007_decorator;

/**
 * Copyright 2018-2028 Baoge All Rights Reserved.
 * Author: Shao Xu Bao <15818589952@163.com>
 * Date:   2020/1/5
 */

// 装饰角色实现抽象角色
public class Decorator implements Component {

    // 装饰角色要持有一个抽象角色引用
    private Component component;

    public Decorator(Component component) {
        this.component = component;
    }

    @Override
    public void doSomething() {
        component.doSomething();
    }
}
