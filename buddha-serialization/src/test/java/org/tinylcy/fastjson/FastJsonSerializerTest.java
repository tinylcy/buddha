package org.tinylcy.fastjson;

import org.junit.Test;
import org.tinylcy.RpcRequest;

import java.util.Arrays;

/**
 * Created by chenyangli.
 */
public class FastJsonSerializerTest {

    @Test
    public void fastJsonTest() {
        RpcRequest request = new RpcRequest("org.tinylcy.services.IHelloService", "hello",
                new Class<?>[]{String.class}, new Object[]{"chenyang"});
        FastJsonSerializer serializer = new FastJsonSerializer();
        byte[] bytes = serializer.serialize(request);
        System.out.println(Arrays.toString(bytes));
        RpcRequest r = serializer.deserialize(bytes, RpcRequest.class);
        System.out.println(r);
    }
}
