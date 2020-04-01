package com.mrgood.noobrpc.codec;

import com.mrgood.noobrpc.model.NoobRpcCallRequest;
import com.mrgood.noobrpc.model.NoobRpcMethod;
import com.mrgood.noobrpc.utils.ProtostuffUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class NoobRpcCallRequestDecoder extends ByteToMessageDecoder {
    private ConcurrentHashMap<String, Class<?>> classCache = new ConcurrentHashMap<>();

    public void decode(ByteBuf in, List<Object> out) throws ClassNotFoundException {
        int methodLength = in.readInt();
        byte[] method = new byte[methodLength];
        in.readBytes(method);
        NoobRpcCallRequest request = new NoobRpcCallRequest();
        request.method = ProtostuffUtils.deserialize(method, NoobRpcMethod.class);
        int argsLength = in.readInt();
        request.arg = new Object[argsLength];
        String[] argsClasses = request.method.getArgsClasses();

        for (int i = 0; i < request.arg.length; i++) {
            Class<?> aClass = classCache.get(argsClasses[i]);
            if (aClass == null) {
                aClass = Class.forName(argsClasses[i]);
                classCache.put(argsClasses[i], aClass);
            }
            int argLength = in.readInt();
            byte[] b = new byte[argLength];
            in.readBytes(b);
            Object o = ProtostuffUtils.deserialize(b, aClass);
            request.arg[i] = o;
        }
        out.add(request);
    }

    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        decode(in, out);
    }
}
