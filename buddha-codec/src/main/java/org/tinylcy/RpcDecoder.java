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

    public RpcDecoder(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list)
            throws Exception {
        if (byteBuf.readableBytes() < 4) {
            return;
        }
        Serializer serializer = SerializerFactory.load();
        int length = byteBuf.readInt();
        if (byteBuf.readableBytes() < length) {
            throw new RuntimeException("Insufficient bytes to be read, expected: " + length);
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
