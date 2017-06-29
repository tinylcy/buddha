package org.tinylcy;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by chenyangli.
 */
public class RpcEncoder extends MessageToByteEncoder {

    private Class<?> clazz;

    public RpcEncoder(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object object, ByteBuf byteBuf)
            throws Exception {
        System.out.println("RpcEncoder - object: " + object);
        Serializer serializer = SerializerFactory.load();
        byte[] bytes = serializer.serialize(object);
        byteBuf.writeBytes(bytes);

    }
}
