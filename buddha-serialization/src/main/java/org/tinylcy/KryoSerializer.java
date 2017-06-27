package org.tinylcy;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by chenyangli.
 */
public class KryoSerializer implements Serializer {

    public byte[] serialize(Object object) {
        Kryo kryo = new Kryo();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Output output = new Output(baos);
        kryo.writeObject(output, object);
        output.flush();
        output.close();
        byte[] bytes = baos.toByteArray();
        try {
            baos.flush();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    public Object deserialize(byte[] bytes, Class<?> clazz) {
        Kryo kryo = new Kryo();
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        Input input = new Input(bais);
        Object result = kryo.readObject(input, clazz);
        System.out.println(result);
        input.close();
        return result;
    }
}
