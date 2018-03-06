package org.tinylcy.concurrence;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import org.tinylcy.RpcRequest;
import org.tinylcy.RpcResponse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by Jun on 2018/3/6.
 */
public class ServerThread implements Runnable {
    private RpcRequest request;
    private ChannelHandlerContext context;
    private Map<String, Object> handlerMap;

    public ServerThread(ChannelHandlerContext context, RpcRequest request, Map<String, Object> handlerMap) {
        this.context = context;
        this.request = request;
        this.handlerMap = handlerMap;
    }

    @Override
    public void run() {
        System.out.println("RpcServerHandler request: " + request + "  Thread ID: " + Thread.currentThread());
        RpcResponse response = new RpcResponse();
        response.setRequestId(request.getRequestId());
        response.setError(null);
        Object result = handle(request);
        response.setResult(result);
        context.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
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
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }
}
