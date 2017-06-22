package org.tinylcy.protocol;

/**
 * Created by chenyangli.
 */
public class BuddhaProtocol {

    public static final byte REQUEST = 0;
    public static final byte RESPONSE = 1;

    private short magic;
    private byte type;
    private long id;         // request id
    private int bodyLength;
    private byte [] body;

    public short getMagic() {
        return magic;
    }

    public void setMagic(short magic) {
        this.magic = magic;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getBodyLength() {
        return bodyLength;
    }

    public void setBodyLength(int bodyLength) {
        this.bodyLength = bodyLength;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}
