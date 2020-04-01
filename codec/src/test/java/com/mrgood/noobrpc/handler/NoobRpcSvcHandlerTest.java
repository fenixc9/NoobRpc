package com.mrgood.noobrpc.handler;

import com.mrgood.noobrpc.codec.*;
import com.mrgood.noobrpc.model.NoobRpcCallRequest;
import com.mrgood.noobrpc.model.NoobRpcMethod;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.LinkedList;
import java.util.List;

public class NoobRpcSvcHandlerTest {

    @Test
    public void testChannelRead0() throws Exception {
        NoobRpcCallRequestEncoder noobRpcCallRequestEncoder = new NoobRpcCallRequestEncoder();
        NoobRpcCallRequestDecoder noobRpcCallRequestDecoder = new NoobRpcCallRequestDecoder();
        NoobRpcCallResponseEncoder noobRpcCallResponseEncoder = new NoobRpcCallResponseEncoder();
        NoobRpcCallResponseDecoder noobRpcCallResponseDecoder = new NoobRpcCallResponseDecoder();

        EmbeddedChannel channel = new EmbeddedChannel(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ch.pipeline()
                        .addLast(new NoobRpcSvcCodec(noobRpcCallRequestDecoder, noobRpcCallResponseEncoder))
                        .addLast(new NoobRpcSvcHandler(new Object[]{new I()}));
            }
        });

        NoobRpcCallRequest request = new NoobRpcCallRequest();
        request.method = new NoobRpcMethod("say", new String[]{"java.lang.Integer"}, "java.lang.String", "com.mrgood.noobrpc.handler.NoobRpcSvcHandlerTest$I");
        request.arg = new Object[]{888888};
        ByteBuf buffer = Unpooled.buffer();
        noobRpcCallRequestEncoder.encode(request, buffer);
        channel.writeInbound(buffer);

        ByteBuf o = channel.readOutbound();
        List<Object> list = new LinkedList<>();
        noobRpcCallResponseDecoder.decode(o, list);
        System.out.println(list.get(0));
    }

    public class I {
        public I() {
        }

        String say(Integer a) {
            System.out.println("impl called");
            return a + " this is impl";
        }
    }


    @Test
    public void channelRead0() {
        I o = (I) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{I.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                int arg = (int) args[0];

                return "123:" + arg;
            }
        });
        String say = o.say(999);
        System.out.println(say);
    }
}