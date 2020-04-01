package com.mrgood.noobrpc.model;

/**
 * 编码方式
 * 方法名长度[4byte]+方法名[byte]+参数个数[4byte][(参数序列化数组的长度+序列化数组)]
 */
public class NoobRpcCallRequest {
    public NoobRpcInterface rpcInterface;
    public NoobRpcMethod method;
    public Object[] arg;
}
