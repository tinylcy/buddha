package org.tinylcy.kryo;

import org.junit.Test;
import org.tinylcy.RpcRequest;

import java.util.Arrays;

/**
 * Created by chenyangli.
 */
public class KryoSerializerTest {

    @Test
    public void kryoTest() {
        RpcRequest request = new RpcRequest("org.tinylcy.services.IHelloService", "hello",
                new Class<?>[]{String.class}, new Object[]{"chenyang"});
        KryoSerializer serializer = new KryoSerializer();
        byte[] bytes = serializer.serialize(request);
        System.out.println(Arrays.toString(bytes));
        RpcRequest r = (RpcRequest) serializer.deserialize(bytes, RpcRequest.class);
        System.out.println(r);

    }

}
