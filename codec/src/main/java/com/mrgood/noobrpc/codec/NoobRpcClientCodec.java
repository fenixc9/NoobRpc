package com.mrgood.noobrpc.codec;

import io.netty.channel.CombinedChannelDuplexHandler;

public class NoobRpcClientCodec extends CombinedChannelDuplexHandler<NoobRpcCallResponseDecoder, NoobRpcCallRequestEncoder> {

    public NoobRpcClientCodec(NoobRpcCallResponseDecoder noobRpcCallRequestDecoder, NoobRpcCallRequestEncoder noobRpcCallRequestEncoder) {
        super(noobRpcCallRequestDecoder, noobRpcCallRequestEncoder);
    }
}
