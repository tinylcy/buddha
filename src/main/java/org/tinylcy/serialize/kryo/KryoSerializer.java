package org.tinylcy.serialize.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.tinylcy.serialize.Serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by chenyangli.
 */
public class KryoSerializer implements Serializer {

    public <T> byte[] serialize(T obj) {
        Kryo kryo = new Kryo();
        // kryo.setReferences(false);
        // kryo.register(obj.getClass(),new JavaSerializer());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Output output = new Output(baos);
        kryo.writeObject(output, obj);
        output.flush();
        output.close();

        byte[] bytes = baos.toByteArray();
        try {
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;

    }

    public <T> T deserialize(byte[] bytes, Class<?> clazz) {
        Kryo kryo = new Kryo();
        // kryo.setReferences(false);
        // kryo.register(clazz, new JavaSerializer());

        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        Input input = new Input(bais);
        return (T) kryo.readObject(input, clazz);
    }

}
