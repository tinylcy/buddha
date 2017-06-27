package org.tinylcy;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by chenyangli.
 */
public class KryoSerializer implements Serializer {

    public byte[] serialize(Object object) {
        Kryo kryo = new Kryo();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Output output = new Output(baos);
        kryo.writeObject(output, baos);
        return baos.toByteArray();
    }

    public Object deserialize(byte[] bytes, Class<?> clazz) {
        Kryo kryo = new Kryo();
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        Input input = new Input(bais);
        return kryo.readObject(input, clazz);
    }
}
