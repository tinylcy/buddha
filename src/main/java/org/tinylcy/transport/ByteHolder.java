package org.tinylcy.transport;

/**
 * Created by chenyangli.
 */
public class ByteHolder {

    private byte[] bytes;

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public int size() {
        return bytes.length;
    }
}
