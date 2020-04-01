package com.mrgood.noobrpc.codec;

import com.mrgood.noobrpc.utils.ProtostuffUtils;
import com.mrgood.noobrpc.model.NoobRpcCallRequest;
import com.mrgood.noobrpc.model.NoobRpcMethod;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class NoobRpcCallRequestEncoder extends MessageToByteEncoder<NoobRpcCallRequest> {
    public void encode(NoobRpcCallRequest msg, ByteBuf out) {
        NoobRpcMethod method = msg.method;
        byte[] methodSerialize = ProtostuffUtils.serialize(method);
        out.writeInt(methodSerialize.length);
        out.writeBytes(methodSerialize);

        out.writeInt(msg.arg.length);
        for (Object o : msg.arg) {
            byte[] serialize = ProtostuffUtils.serialize(o);
            out.writeInt(serialize.length);
            out.writeBytes(serialize);
        }
    }

    protected void encode(ChannelHandlerContext ctx, NoobRpcCallRequest msg, ByteBuf out) throws Exception {
        encode(msg, out);
    }

}
