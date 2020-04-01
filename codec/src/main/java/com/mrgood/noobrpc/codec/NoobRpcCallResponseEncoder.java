package com.mrgood.noobrpc.codec;

import com.mrgood.noobrpc.model.NoobRpcCallResponse;
import com.mrgood.noobrpc.model.NoobRpcMethod;
import com.mrgood.noobrpc.utils.ProtostuffUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;


public class NoobRpcCallResponseEncoder extends MessageToByteEncoder<NoobRpcCallResponse> {

    protected void encode(ChannelHandlerContext ctx, NoobRpcCallResponse msg, ByteBuf out) throws Exception {
        encode(msg, out);
    }

    public void encode(NoobRpcCallResponse msg, ByteBuf out) throws Exception {
        NoobRpcMethod method = msg.method;
        byte[] methodSerialize = ProtostuffUtils.serialize(method);
        out.writeInt(methodSerialize.length);
        out.writeBytes(methodSerialize);

        if (msg.exception == null) {
            out.writeBoolean(true);
            byte[] serialize = ProtostuffUtils.serialize(msg.ret);
            out.writeBoolean(true);
            out.writeInt(serialize.length);
            out.writeBytes(serialize);
        } else {
            byte[] serialize = ProtostuffUtils.serialize(msg.exception);
            out.writeBoolean(false);
            out.writeInt(serialize.length);
            out.writeBytes(serialize);
        }

    }
}
