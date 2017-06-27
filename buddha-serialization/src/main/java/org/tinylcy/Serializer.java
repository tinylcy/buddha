package org.tinylcy;

/**
 * Created by chenyangli.
 */
public interface Serializer {

    byte[] serialize(Object object);

    Object deserialize(byte[] bytes, Class<?> clazz);
}
