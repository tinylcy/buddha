package org.tinylcy;

import com.google.common.io.ByteSource;
import com.google.common.io.Resources;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * Created by chenyangli.
 * <p>
 * Read a properties file and get property.
 */
public class PropertyReader {

    private String path;

    public PropertyReader(String path) {
        this.path = path;
    }

    public String getProperty(String key) {
        final URL url = Resources.getResource(path);
        final ByteSource byteSource = Resources.asByteSource(url);
        final Properties properties = new Properties();
        InputStream inputStream = null;
        String value = null;
        try {
            inputStream = byteSource.openBufferedStream();
            properties.load(inputStream);
            value = properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return value;
    }
}
