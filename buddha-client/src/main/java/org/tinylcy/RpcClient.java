package org.tinylcy;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.net.InetSocketAddress;

/**
 * Created by chenyangli.
 */
public class RpcClient {

    private static final Logger LOGGER = Logger.getLogger(RpcClient.class);

    public Object send(final Class<?> clazz, Method method, Object[] args) {
        RpcRequest request = new RpcRequest("", clazz.getName(),
                method.getName(), method.getParameterTypes(), args);
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        final RpcResponse response = new RpcResponse();
        try {
            bootstrap.group(group).channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline().addLast(new RpcEncoder(RpcRequest.class));
                            channel.pipeline().addLast(new RpcDecoder(RpcResponse.class));
                            channel.pipeline().addLast(new RpcClientHandler(response));
                        }
                    }).option(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = bootstrap.connect(new InetSocketAddress("localhost", 9090)).sync();
            future.channel().writeAndFlush(request).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            LOGGER.error("RpcClient send RPC error.");
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }

        return response.getResult();
    }

}
