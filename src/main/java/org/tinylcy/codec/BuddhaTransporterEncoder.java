package org.tinylcy.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.tinylcy.transport.BuddhaTransporter;

/**
 * Created by chenyangli.
 */
public class BuddhaTransporterEncoder extends MessageToByteEncoder<BuddhaTransporter> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, BuddhaTransporter buddhaTransporter,
                          ByteBuf byteBuf) throws Exception {


    }
}
