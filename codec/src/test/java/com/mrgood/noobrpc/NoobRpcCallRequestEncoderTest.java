package com.mrgood.noobrpc;

import com.google.gson.Gson;
import com.mrgood.noobrpc.codec.NoobRpcCallRequestDecoder;
import com.mrgood.noobrpc.codec.NoobRpcCallRequestEncoder;
import com.mrgood.noobrpc.model.NoobRpcCallRequest;
import com.mrgood.noobrpc.model.NoobRpcMethod;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class NoobRpcCallRequestEncoderTest<T> {
    class Dummy {

    }

    @Test
    public void t1() throws ClassNotFoundException {

    }

    @Test
    public void tt2() throws ClassNotFoundException {

    }

    /**
     * new StringEncoder(CharsetUtil.UTF_8),
     * new LineBasedFrameDecoder(8192),
     * new StringDecoder(CharsetUtil.UTF_8),
     * new ChunkedWriteHandler()
     *
     * @throws InterruptedException
     */
    @Test
    public void test() throws InterruptedException {
        ByteBuf buf = Unpooled.buffer();
        EmbeddedChannel channel = new EmbeddedChannel(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ch.pipeline()
                        .addLast(new StringEncoder(CharsetUtil.UTF_8))
                        .addLast(new StringDecoder(CharsetUtil.UTF_8))
                        .addLast(new SimpleChannelInboundHandler<String>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
                                System.out.println("embed got:" + s);
                                channelHandlerContext.writeAndFlush("yes rpg");
                            }
                        });
            }
        });
        buf.writeBytes("hello world".getBytes());
        channel.writeInbound(buf);
        Object o = channel.readInbound();
        System.out.println(o);
        ByteBuf o1 = channel.readOutbound();
//        byte[] b = new byte[o1.readableBytes()];
//        o1.readBytes(o1.readableBytes());
//        System.out.println(new String(b));
    }

    @Test
    public void t3() throws ClassNotFoundException {
        NoobRpcCallRequestDecoder noobRpcCallRequestDecoder = new NoobRpcCallRequestDecoder();
        NoobRpcCallRequestEncoder encoder = new NoobRpcCallRequestEncoder();
        EmbeddedChannel channel = new EmbeddedChannel(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ch.pipeline()
                        .addLast(noobRpcCallRequestDecoder)
                        .addLast(encoder)
                        .addLast(new SimpleChannelInboundHandler<NoobRpcCallRequest>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext channelHandlerContext, NoobRpcCallRequest s) throws Exception {
                                System.out.println("embed got:" + s.method.toString());
                                s.arg[0] = "jony";
                                channelHandlerContext.writeAndFlush(s);
                            }
                        });
            }
        });
        NoobRpcMethod method = new NoobRpcMethod("getName", new String[]{"java.lang.String", "java.lang.Integer"}, "java.lang.String","I");
        NoobRpcCallRequest req = new NoobRpcCallRequest();
        req.method = method;
        req.arg = new Object[]{"tommy", 23};
        ByteBuf buffer = Unpooled.buffer();
        encoder.encode(req,buffer);
        channel.writeInbound(buffer);
        ByteBuf o = channel.readOutbound();
        List<Object> objects = new LinkedList<>();
        noobRpcCallRequestDecoder.decode(o,objects);
        System.out.println(objects);
    }

    @Test
    public void t5(){
        EmbeddedChannel channel = new EmbeddedChannel(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ch.pipeline()
                        .addLast(new NoobRpcCallRequestDecoder())
                        .addLast(new NoobRpcCallRequestEncoder())
                        .addLast(new SimpleChannelInboundHandler<NoobRpcCallRequest>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext channelHandlerContext, NoobRpcCallRequest s) throws Exception {
                                System.out.println("embed got:" + s.method.toString());
                                s.arg[0] = "jony";
                                channelHandlerContext.writeAndFlush(s);
                            }
                        });
            }
        });

    }

}