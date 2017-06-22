package org.tinylcy.serialize;

import com.sun.tools.javac.util.ServiceLoader;

/**
 * Created by chenyangli.
 */
public class SerializerFactory {

    /**
     * SPI
     *
     * @return
     */
    public static Serializer loadSerializer() {
        return ServiceLoader.load(Serializer.class).iterator().next();
    }
}
