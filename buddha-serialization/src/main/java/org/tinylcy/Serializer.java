package org.tinylcy;

/**
 * Created by chenyangli.
 */
public interface Serializer {

    byte[] serialize(Object object);

    <T> T deserialize(byte[] bytes, Class<T> clazz);
}
