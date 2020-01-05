package com.baoge.netty.demo_007_decorator;

/**
 * Copyright 2018-2028 Baoge All Rights Reserved.
 * Author: Shao Xu Bao <15818589952@163.com>
 * Date:   2020/1/5
 */
public class ConcreteDecorator2 extends Decorator {

    public ConcreteDecorator2(Component component) {
        super(component);
    }

    // 重写
    @Override
    public void doSomething() {
        super.doSomething();
        this.doAnotherThing();
    }

    // 重写包装后，增加额外功能
    private void doAnotherThing() {
        System.out.println("功能C");
    }

}
