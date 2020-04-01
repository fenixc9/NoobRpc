package com.mrgood.noobrpc.model;

/**
 * 编码方式
 * 方法名长度[4byte]+方法名[byte]+是否是异常boolean？异常长度[4byte]+异常序列化数组：返回对象长度[4byte]+返回对象序列化数组
 */
public class NoobRpcCallResponse {
    public NoobRpcMethod method;
    public Object ret;
    public NoobRpcException exception;

    @Override
    public String toString() {
        return "NoobRpcCallResponse{" +
                "method=" + method +
                ", ret=" + ret +
                '}';
    }
}
