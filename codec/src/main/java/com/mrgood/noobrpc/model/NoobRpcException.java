package com.mrgood.noobrpc.model;

public class NoobRpcException extends Exception {
    public NoobRpcException(String msg) {
        super(msg);
    }

    public NoobRpcException(Exception cause) {
        super(cause);
    }
}
