package org.tinylcy;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by chenyangli.
 */
public class ApplicationContextTest {

    @Test
    public void testApplicationContext() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Object manager = context.getBean("zk-manager");
        System.out.println(manager);
        Object registry = context.getBean("buddha-registry");
        System.out.println(registry);
        Object server = context.getBean("buddha-rpc-server");
        System.out.println(server);
    }
}
