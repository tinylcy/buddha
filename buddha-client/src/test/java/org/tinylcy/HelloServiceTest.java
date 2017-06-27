package org.tinylcy;

import org.junit.Test;
import org.tinylcy.proxy.RpcProxy;
import org.tinylcy.service.IHelloService;

/**
 * Created by chenyangli.
 */
public class HelloServiceTest {

    @Test
    public void testHello() {
        RpcClient client = new RpcClient();
        RpcProxy proxy = new RpcProxy(IHelloService.class, client);
        IHelloService service = proxy.newProxy();
        String result = service.hello("chenyang");
        System.out.println(result);
    }
}
