package com.mrgood.noobrpc;

public class Main {
    public static void main(String[] args) {
        SvcConsumer svcProvider = new SvcConsumerImpl();

        HelloWorldService svc = svcProvider.getSvc();
        HelloDummy tommy = svc.sayHello("tommy", 123);

    }
}
