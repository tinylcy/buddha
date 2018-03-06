package org.tinylcy;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.tinylcy.proxy.RpcProxy;
import org.tinylcy.service.IHelloService;

/**
 * Created by chenyangli.
 */
public class HelloServiceTest {

    @Test
    public void helloTest() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        RpcProxy proxy = (RpcProxy) context.getBean("buddha-rpc-proxy");
        IHelloService service = proxy.newProxy(IHelloService.class);
        for (int i = 0; i < 10; i++) {
            String result = service.hello("chenyang");
            System.out.println(result);
        }

    }

}
