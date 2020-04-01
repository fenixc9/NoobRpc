package com.mrgood.noobrpc.codec;

import com.mrgood.noobrpc.codec.NoobRpcCallRequestDecoder;
import com.mrgood.noobrpc.codec.NoobRpcCallResponseEncoder;
import io.netty.channel.CombinedChannelDuplexHandler;

public class NoobRpcSvcCodec extends CombinedChannelDuplexHandler<NoobRpcCallRequestDecoder, NoobRpcCallResponseEncoder> {
    public NoobRpcSvcCodec(NoobRpcCallRequestDecoder inboundHandler, NoobRpcCallResponseEncoder outboundHandler) {
        super(inboundHandler, outboundHandler);
    }
}
