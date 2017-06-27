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
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcResponse response)
            throws Exception {
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RpcResponse res = (RpcResponse) msg;
        response.setRequestId(res.getRequestId());
        response.setError(res.getError());
        response.setResult(res.getResult());
        ctx.writeAndFlush(response);
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
