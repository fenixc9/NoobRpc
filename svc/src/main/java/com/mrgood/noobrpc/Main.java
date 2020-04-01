package com.mrgood.noobrpc;

public class Main {
    public static void main(String[] args) {
        SvcProvider svcProvider = new SvcProviderImpl();
        HelloWorldService svc = svcProvider.getSvc();
    }
}
