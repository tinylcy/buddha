package org.tinylcy;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.concurrent.CountDownLatch;

/**
 * Created by chenyangli.
 */
public class RpcClient {

    private static final Logger LOGGER = Logger.getLogger(RpcClient.class);

    private ServiceDiscovery discovery;

    public RpcClient(ServiceDiscovery discovery) {
        this.discovery = discovery;
    }

    public Object call(final Class<?> clazz, Method method, Object[] args) {
        RpcRequest request = new RpcRequest(clazz.getName(),
                method.getName(), method.getParameterTypes(), args);
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        final RpcResponse response = new RpcResponse();
        try {
            final CountDownLatch completedSignal = new CountDownLatch(1);
            bootstrap.group(group).channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline().addLast(new RpcEncoder(RpcRequest.class));
                            channel.pipeline().addLast(new RpcDecoder(RpcResponse.class));
                            channel.pipeline().addLast(new RpcClientHandler(response));
                        }
                    }).option(ChannelOption.SO_KEEPALIVE, true);

            String connectString = discovery.discover();
            String[] tokens = connectString.split(":");
            String host = tokens[0];
            int port = Integer.parseInt(tokens[1]);
            ChannelFuture future = bootstrap.connect(new InetSocketAddress(host, port)).sync();
            future.channel().writeAndFlush(request).addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    completedSignal.countDown();
                }
            });
            // Wait until the asynchronous write return.
            completedSignal.await();

            future.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            LOGGER.error("RpcClient call RPC failure.");
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }

        return response;
    }

}
