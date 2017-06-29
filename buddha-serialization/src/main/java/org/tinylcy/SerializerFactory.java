package org.tinylcy;

import java.util.ServiceLoader;

/**
 * Created by chenyangli.
 */
public class SerializerFactory {

    /**
     * SPI
     *
     * @return
     */
    public static Serializer load() {
        return ServiceLoader.load(Serializer.class).iterator().next();
    }

}
