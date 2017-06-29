package org.tinylcy;

import org.junit.Test;

/**
 * Created by chenyangli.
 */
public class PropertyReaderTest {

    @Test
    public void read() {
        PropertyReader reader = new PropertyReader("buddha-server.properties");
        String host = reader.getProperty("server.address.host");
        System.out.println(host);
    }
}
