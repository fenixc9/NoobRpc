package com.mrgood.noobrpc;

import com.mrgood.noobrpc.codec.*;
import com.mrgood.noobrpc.model.NoobRpcCallRequest;
import com.mrgood.noobrpc.model.NoobRpcCallResponse;
import com.mrgood.noobrpc.model.NoobRpcMethod;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class NoobRpcClientCodecTest {
    @Test
    public void t1() throws Exception {
    }

}