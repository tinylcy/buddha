package org.tinylcy;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.CountDownLatch;

/**
 * Created by chenyangli.
 */
public class RpcClientHandler extends SimpleChannelInboundHandler<RpcResponse> {

    private RpcResponse response;
    private CountDownLatch latch;

    public RpcClientHandler(RpcResponse response, CountDownLatch latch) {
        this.response = response;
        this.latch = latch;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext context, RpcResponse resp)
            throws Exception {
        response.setRequestId(resp.getRequestId());
        response.setError(resp.getError());
        response.setResult(resp.getResult());
        latch.countDown();
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
