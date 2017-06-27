package org.tinylcy;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Map;

/**
 * Created by chenyangli.
 */
public class RpcHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private Map<String, Object> handlerMap;

    public RpcHandler(Map<String, Object> handlerMap) {
        this.handlerMap = handlerMap;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcRequest request)
            throws Exception {
        RpcResponse response = new RpcResponse();
        response.setRequestId(request.getRequestId());
        handle(request);
    }

    private Object handle(RpcRequest request) {
        String className = request.getClassName();
        Object serivceBean = handlerMap.get(className);
        System.out.println("class name: " + className);
        return null;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
