package com.mrgood.noobrpc.codec;

import com.mrgood.noobrpc.model.NoobRpcCallResponse;
import com.mrgood.noobrpc.model.NoobRpcException;
import com.mrgood.noobrpc.model.NoobRpcMethod;
import com.mrgood.noobrpc.utils.ProtostuffUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class NoobRpcCallResponseDecoder extends ByteToMessageDecoder {
    private ConcurrentHashMap<String, Class<?>> classCache = new ConcurrentHashMap<>();

    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        decode(in, out);
    }

    public void decode(ByteBuf in, List<Object> out) throws Exception {
        int methodLength = in.readInt();
        byte[] method = new byte[methodLength];
        in.readBytes(method);

        NoobRpcCallResponse response = new NoobRpcCallResponse();
        response.method = ProtostuffUtils.deserialize(method, NoobRpcMethod.class);

        boolean isException = in.readBoolean();
        if (!isException) {
            Class<?> aClass = classCache.get(response.method.getRetClass());
            if (aClass == null) {
                aClass = Class.forName(response.method.getRetClass());
                classCache.put(response.method.getRetClass(), aClass);
            }

            int argLength = in.readInt();
            byte[] b = new byte[argLength];
            in.readBytes(b);
            response.ret = ProtostuffUtils.deserialize(b, aClass);
            out.add(response);
        } else {
            int exception = in.readInt();
            byte[] b = new byte[exception];
            in.readBytes(b);
            response.exception = ProtostuffUtils.deserialize(b, NoobRpcException.class);
            out.add(response);
        }
    }
}
