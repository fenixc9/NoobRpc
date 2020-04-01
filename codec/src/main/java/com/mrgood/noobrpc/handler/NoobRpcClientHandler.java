package com.mrgood.noobrpc.handler;

import com.mrgood.noobrpc.model.NoobRpcCallRequest;
import com.mrgood.noobrpc.model.NoobRpcCallResponse;
import com.mrgood.noobrpc.model.NoobRpcMethod;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

public class NoobRpcClientHandler extends SimpleChannelInboundHandler<NoobRpcCallResponse> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, NoobRpcCallResponse msg) throws Exception {

    }
}
