package org.tinylcy;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by chenyangli.
 */
public class RpcDecoder extends ByteToMessageDecoder {

    private Class<?> clazz;

    public RpcDecoder() {
    }

    public RpcDecoder(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list)
            throws Exception {
        Serializer serializer = new KryoSerializer();
        int length = byteBuf.readableBytes();
        if (length < 0) {
            throw new RuntimeException("RpcDecoder: the count of bytes can not be < 0: " + length);
        }
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);
        Object object = serializer.deserialize(bytes, clazz);
        list.add(object);
    }

    public void setClass(Class<?> clazz) {
        this.clazz = clazz;
    }
}
