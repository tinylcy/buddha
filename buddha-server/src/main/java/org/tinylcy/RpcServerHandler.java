package org.tinylcy;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by chenyangli.
 */
public class RpcServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private Map<String, Object> handlerMap;

    public RpcServerHandler(Map<String, Object> handlerMap) {
        this.handlerMap = handlerMap;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext context, RpcRequest request)
            throws Exception {
        System.out.println("RpcServerHandler - request: " + request);

        RpcResponse response = new RpcResponse();
        response.setRequestId(request.getRequestId());
        response.setError(null);
        Object result = handle(request);
        response.setResult(result);
        context.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("RpcClient channelReadComplete");
    }

    private Object handle(RpcRequest request) {
        String className = request.getClassName();
        Object serviceBean = handlerMap.get(className);
        if (serviceBean == null) {

        }
        Method[] methods = serviceBean.getClass().getDeclaredMethods();
        Object result = null;
        try {
            for (Method method : methods) {
                if (method.getName().equals(request.getMethodName())) {
                    result = method.invoke(serviceBean, request.getParams());
                    break;
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
