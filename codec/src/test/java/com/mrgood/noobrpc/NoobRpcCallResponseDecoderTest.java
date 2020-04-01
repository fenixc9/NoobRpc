package com.mrgood.noobrpc;

import com.mrgood.noobrpc.codec.NoobRpcCallResponseDecoder;
import com.mrgood.noobrpc.codec.NoobRpcCallResponseEncoder;
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

public class NoobRpcCallResponseDecoderTest {

    @Test
    public void decode() {
    }

    @Test
    public void testDecode() throws Exception {
        NoobRpcCallResponse response = new NoobRpcCallResponse();
        response.ret = "return message";
        response.method = new NoobRpcMethod("getName", new String[]{"java.lang.String"}, "java.lang.Integer","I");
        NoobRpcCallResponseDecoder noobRpcCallResponseDecoder = new NoobRpcCallResponseDecoder();
        NoobRpcCallResponseEncoder noobRpcCallResponseEncoder = new NoobRpcCallResponseEncoder();

        EmbeddedChannel channel = new EmbeddedChannel(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ch.pipeline()
                        .addLast(noobRpcCallResponseDecoder)
                        .addLast(noobRpcCallResponseEncoder)
                        .addLast(new SimpleChannelInboundHandler<NoobRpcCallResponse>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext channelHandlerContext, NoobRpcCallResponse s) throws Exception {
                                System.out.println("embed got:" + s.method.toString());
                                s.ret = "shit";
                                channelHandlerContext.writeAndFlush(s);
                            }
                        });
            }
        });

        NoobRpcMethod method = new NoobRpcMethod("getName", new String[]{"java.lang.String", "java.lang.Integer"}, "java.lang.String","I");
        NoobRpcCallResponse req = new NoobRpcCallResponse();
        req.method = method;
        req.ret = "yes rpg";
        ByteBuf buffer = Unpooled.buffer();
        noobRpcCallResponseEncoder.encode(req, buffer);
        channel.writeInbound(buffer);

        ByteBuf o = channel.readOutbound();
        List<Object> objects = new LinkedList<>();
        noobRpcCallResponseDecoder.decode(o, objects);
        System.out.println(objects);
    }
}