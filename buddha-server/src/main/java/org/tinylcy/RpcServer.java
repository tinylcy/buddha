package org.tinylcy;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.commons.collections4.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.tinylcy.annotation.RpcService;

import javax.imageio.spi.ServiceRegistry;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenyangli.
 */
public class RpcServer implements ApplicationContextAware {

    private static final Logger LOGGER = Logger.getLogger(RpcServer.class);

    private String serverAddress;
    private ServiceRegistry registry;
    private Map<String, Object> handlerMap = new HashMap<String, Object>();

    public RpcServer(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public RpcServer(String serverAddress, ServiceRegistry registry) {
        this.serverAddress = serverAddress;
        this.registry = registry;
    }

    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        Map<String, Object> serviceMap = applicationContext.getBeansWithAnnotation(RpcService.class);
        if (MapUtils.isNotEmpty(serviceMap)) {
            for (Object bean : serviceMap.values()) {
                String interfaceName = bean.getClass().getAnnotation(RpcService.class).value().getName();
                handlerMap.put(interfaceName, bean);
            }
        }
    }

    public void bootstrap() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline().addLast(new RpcEncoder(RpcResponse.class));
                            channel.pipeline().addLast(new RpcDecoder(RpcRequest.class));
                            channel.pipeline().addLast(new RpcServerHandler(handlerMap));
                        }
                    }).option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = bootstrap.bind(new InetSocketAddress(9090)).sync();
            LOGGER.info("RpcServer start successfully, listening on port: " + 9090);
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            LOGGER.error("RpcServer bind failure.");
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) {
        RpcServer server = new RpcServer(null);
        server.setApplicationContext(new ClassPathXmlApplicationContext("applicationContext.xml"));
        server.bootstrap();
    }

}
