package org.tinylcy;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import java.util.List;

/**
 * Created by chenyangli.
 */
public class RpcRequestCodec extends ByteToMessageCodec<RpcRequest> {

    @Override
    protected void encode(ChannelHandlerContext context, RpcRequest request, ByteBuf byteBuf)
            throws Exception {
        Serializer serializer = SerializerFactory.load();
        byte[] bytes = serializer.serialize(request);
        int len = bytes.length;
        byteBuf.writeInt(len);
        byteBuf.writeBytes(bytes);
    }

    @Override
    protected void decode(ChannelHandlerContext context, ByteBuf byteBuf, List<Object> list)
            throws Exception {
        if (byteBuf.readableBytes() < 4) {
            return;
        }
        int len = byteBuf.readInt();
        if (byteBuf.readableBytes() < len) {
            throw new RuntimeException("Insufficient bytes to be read, expected: " + len);
        }
        byte[] bytes = new byte[len];
        byteBuf.readBytes(bytes);
        Serializer serializer = SerializerFactory.load();
        Object object = serializer.deserialize(bytes, RpcRequest.class);
        list.add(object);
    }
}
