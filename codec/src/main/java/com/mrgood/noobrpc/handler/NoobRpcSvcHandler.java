package com.mrgood.noobrpc.handler;

import com.mrgood.noobrpc.model.NoobRpcCallRequest;
import com.mrgood.noobrpc.model.NoobRpcCallResponse;
import com.mrgood.noobrpc.model.NoobRpcException;
import com.mrgood.noobrpc.model.NoobRpcMethod;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

public class NoobRpcSvcHandler extends SimpleChannelInboundHandler<NoobRpcCallRequest> {
    public NoobRpcSvcHandler(Object[] interfaces) {
        makeNoobRpcInterface(interfaces);
    }

    private ConcurrentHashMap<NoobRpcMethod, Method> methodCache = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Object> objectCache = new ConcurrentHashMap<>();

    private void makeNoobRpcInterface(Object[] interfaces) {
        for (Object o : interfaces) {
            Class<?> anInterface = o.getClass();
            if (anInterface.isInterface()) {
                throw new IllegalArgumentException("impl only");
            }
            Method[] declaredMethods = anInterface.getDeclaredMethods();
            if (declaredMethods.length > 0) {
                for (Method declaredMethod : declaredMethods) {
                    NoobRpcMethod noobRpcMethod = makeNoobRpcMethod(anInterface, declaredMethod);
                    methodCache.put(noobRpcMethod, declaredMethod);
                }
            }
            objectCache.put(anInterface.getName(), o);
        }
    }

    private NoobRpcMethod makeNoobRpcMethod(Class<?> c, Method declaredMethod) {
        Class<?> returnType = declaredMethod.getReturnType();
        Class<?>[] parameterTypes = declaredMethod.getParameterTypes();
        String[] parameterString = new String[parameterTypes.length];
        if (parameterTypes.length > 0) {
            for (int i = 0; i < parameterTypes.length; i++) {
                parameterString[i] = parameterTypes[i].getName();
            }
        }
        return new NoobRpcMethod(
                declaredMethod.getName(),
                parameterString,
                returnType.getName(),
                c.getName()
        );

    }

    protected void channelRead0(ChannelHandlerContext ctx, NoobRpcCallRequest msg) throws Exception {
        NoobRpcMethod method = msg.method;
        Method target = methodCache.get(method);
        if (target == null) {
            NoobRpcCallResponse response = new NoobRpcCallResponse();
            response.method = method;
            response.exception = new NoobRpcException("rpc method not found");
            ctx.writeAndFlush(response);
        } else {
            NoobRpcCallResponse response = new NoobRpcCallResponse();
            response.method = method;
            try {
                response.ret = target.invoke(objectCache.get(msg.method.getInterfaceName()), msg.arg);
            } catch (Exception e) {
                e.printStackTrace();
                response.exception = new NoobRpcException(e);
            }
            ctx.writeAndFlush(response);
        }
    }
}
