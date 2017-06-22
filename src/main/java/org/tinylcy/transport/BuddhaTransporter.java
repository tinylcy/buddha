package org.tinylcy.transport;

import org.tinylcy.protocol.BuddhaProtocol;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by chenyangli.
 */
public class BuddhaTransporter extends ByteHolder {

    private static final AtomicLong REQUEST_UUID = new AtomicLong(0L);

    /**
     * Type == 0 represents request message
     * and type == 1 represents response message.
     */
    private byte type;

    /**
     * Request message unique ID.
     */
    private long requestId = REQUEST_UUID.getAndIncrement();

    /**
     *
     */
    private TransportBody transportBody;

    /**
     * Create a request transporter.
     *
     * @param body
     * @return
     */
    public static BuddhaTransporter createRequestTransporter(TransportBody body) {
        BuddhaTransporter transporter = new BuddhaTransporter();
        transporter.setType(BuddhaProtocol.REQUEST);
        transporter.setTransportBody(body);
        return transporter;
    }

    /**
     * Create a response transporter.
     *
     * @param body
     * @return
     */
    public static BuddhaTransporter createResponseTransporter(TransportBody body) {
        BuddhaTransporter transporter = new BuddhaTransporter();
        transporter.setType(BuddhaProtocol.RESPONSE);
        transporter.setTransportBody(body);
        return transporter;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public TransportBody getTransportBody() {
        return transportBody;
    }

    public void setTransportBody(TransportBody transportBody) {
        this.transportBody = transportBody;
    }
}
