package org.tinylcy;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by chenyangli.
 */
public class RpcClientHandler extends SimpleChannelInboundHandler<RpcResponse> {

    private RpcResponse response;

    public RpcClientHandler(RpcResponse response) {
        this.response = response;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext context, RpcResponse resp)
            throws Exception {
        System.out.println("RpcClientHandler - response: " + resp);
        response.setRequestId(resp.getRequestId());
        response.setError(resp.getError());
        response.setResult(resp.getResult());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    public RpcResponse getResponse() {
        return response;
    }
}
