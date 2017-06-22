package org.tinylcy.serialize;

/**
 * Created by chenyangli.
 */
public interface Serializer {

    <T> byte[] serialize(T obj);

    <T> T deserialize(byte[] bytes, Class<?> clazz);
}
